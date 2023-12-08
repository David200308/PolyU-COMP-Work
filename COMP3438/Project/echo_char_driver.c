#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/fs.h>
#include <asm/uaccess.h>
#include <linux/cdev.h>

#define DRIVER_NAME "write_char_driver" // Driver Name
#define N_D 1 // Max number of devices using this driver
#define S_N 1 // Start of the minor number


/*
    Variable: devno - Store Major Number & Minor Number
    Data Type: dev_t data type
    dev_t Data Type: __u32 (unsigned 32 bits int)
*/
static dev_t devno;

static int major;

static struct cdev dev_profile;


static int open_impl(struct inode *p_inode, struct file *p_file) {
    printk("Driver " DRIVER_NAME " open.\n");
    return 0;
}

static int write_impl(struct file *p_file, char __user *p_buf, size_t size, loff_t *p_loff) {
    char msg[256];
    int num;
    int ret;
    if (size < strlen(msg) + 1)
        num = size;
    else
        num = strlen(msg) + 1;

    ret = copy_from_user(msg, p_buf, num);

    if (ret) {
        printk("Fall to copy data from the kernel space to the user space!\n");
        return 0;
    }
    
    printk("The msg is %s.\n", msg);
    return num;
}

static int release_impl(struct inode *p_inode, struct file *p_file) {
    printk("Driver " DRIVER_NAME " closed.\n");
    return 0;
}


static struct file_operations fops = {
    owner: THIS_MODULE,
    open: open_impl,
    write: write_impl,
    release: release_impl,
};


static int __init helloworld_char_driver_init(void) {
    int ret;

    /*
        Linux Kernel Function: alloc_chrdev_region() - register a range of char device numbers
    */
    ret = alloc_chrdev_region(&devno, S_N, N_D, DRIVER_NAME);

    if (ret < 0) {
        printk("Driver " DRIVER_NAME " cannot get major number.\n");
        return ret;
    }

    // get the major number
    major = MAJOR(devno);
    printk("Driver " DRIVER_NAME " initialized (Major number %d).\n", major);

    /*
        register a char driver
        cdev_add() - report the device driver to kernel
    */
    cdev_init(&dev_profile, &fops);
    dev_profile.owner = THIS_MODULE;
    dev_profile.ops = &fops;
    ret = cdev_add(&dev_profile, devno, N_D);

    if (ret) {
        printk("Driver " DRIVER_NAME " register fail.\n");
        return ret;
    }
    return 0;
}


static void __exit helloworld_char_driver_exit(void) {
    /*
        delete the dev_profile from register
        cdev_del()
    */
    cdev_del(&dev_profile);
    unregister_chrdev_region(devno, N_D);
    printk("Driver " DRIVER_NAME " register fail.\n");
}


module_init(helloworld_char_driver_init);
module_exit(helloworld_char_driver_exit);


/*
    Module Introduce
*/
MODULE_LICENSE("GPL");
MODULE_AUTHOR("David Jiang <guanlin.jiang@connect.polyu.hk>");
MODULE_DESCRIPTION("Hello world character device driver");
