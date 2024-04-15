## COMP3334 - Assignment
## Exercise 4
## JIANG GUANLIN (21093962D)

import hashlib

def find_near_collisions(studentid):
    n = 34
    hashNearCollisions = {}
    count = 0

    while True:
        ## get the random data
        data = (studentid + str(count)).encode()

        ## Compute the hash using sha256
        hashHax = hashlib.sha256(data).hexdigest()

        ## Convert to binary and pad with zeros
        hashBin = bin(int(hashHax, 16)) [2:] .zfill(256)

        hashPrefix = hashBin[:n]

        ## Check if the prefix is already in hashNearCollisions
        ## if yes, return the tuple with the value, the studentid and the tries
        if (hashPrefix in hashNearCollisions):
            return (hashNearCollisions[hashPrefix], studentid, count)
        else:
            hashNearCollisions[hashPrefix] = data
        
        count += 1


def get_values():
    return (b'2109396295663', b'21093962100389')


def main():
    studentid = "21093962"

    ex4_1_result = find_near_collisions(studentid)
    ex4_2_result = get_values()

    print("Near collisions:", ex4_1_result)
    print("Values:", ex4_2_result)


main()
