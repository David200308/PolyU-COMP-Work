## README:
## by JIANG GUANLIN (21093962D)

## This is a MIPS assembly code program to convert a number from base 10 to base 2, base 4, and base 8.
## User need to use QTSpim to run this program.
## And input the number, then the program will print the result of this number in base 2, base 4, and base 8.

## The code is design to few parts:
## - ask for input part --> let user know to input the number
## - input part --> user input the number
## - binary part --> convert the number to base 2
## - quaternary part --> convert the number to base 4
## - octal part --> convert the number to base 8
## - isContinue part --> ask user if they want to continue the program
## - bye part --> exit the program and print bye

# Here is the code:

    .text
    .globl main

main:
    # ask user to input the number
    la $a0, ask
    li $v0, 4
    syscall

    # Read the number from user input
    li $v0, 5
    syscall

    move $t0, $v0

    # Input Number Part
    li $v0, 4
    la $a0, number
    syscall

    move $a0, $t0
    li $v0,1
    syscall


# Binary Part
    li $v0, 4 # load the "Binary" part to $v0
    la $a0, binary
    syscall # print the result from $a0 which is binary data
    li $t1, 32 # $t1 = 32

BinaryPart:
    add $t1, $t1, -1 # $t1 = $t1 - 1
    move $t2, $t0 # $t2 = $t0
    li $t3, 0 # $t3 = 0
    srl $t3, $t2, $t1 # shift right $t1 bits for $t2 and store in $t3
    rem $t3, $t3, 2 # $t3 = $t3 % 2 (base 2)
    move $a0, $t3 # copy $t3 to $a0
    li $v0, 1
    syscall # print the result from $a0
    bnez $t1, BinaryPart # if $t1 != 0 --> BinaryPart function (loop)


# Quaternary Part
    li $v0, 4 # load the "Quaternary" part to $v0
    la $a0, quaternary
    syscall
    li $t1, 32

QuaternaryPart:
    add $t1, $t1, -2 # $t1 = $t1 - 2
    move $t2, $t0
    li $t3, 0
    srl $t3, $t2, $t1 # shift right $t1 bits for $t2 and store in $t3
    rem $t3, $t3, 4 # $t3 = $t3 % 4 (base 4)
    move $a0, $t3
    li $v0, 1
    syscall
    bnez $t1, QuaternaryPart # if $t1 != 0 --> QuaternaryPart function (loop)


# Octal Part
    li $v0, 4
    la $a0, octal
    syscall
    li $t1, 30
    move $t2, $t0
    li $t3, 0
    srl $t3, $t2, $t1 # shift right $t1 bits for $t2 and store in $t3
    rem $t3, $t3, 8
    move $a0, $t3 # copy $t3 to $a0
    li $v0, 1
    syscall # print the result from $a0

OctalPart:
    add $t1, $t1, -3
    move $t2, $t0
    li $t3, 0
    srl $t3, $t2, $t1 # shift right $t1 bits for $t2 and store in $t3
    rem $t3, $t3, 8 # $t3 = $t3 % 8 (base 8)
    move $a0, $t3
    li $v0, 1
    syscall
    bnez $t1, OctalPart # if $t1 != 0 --> OctalPart function (loop)

    
# isContinue Part
    li $v0, 4 # load the "ask user to input the number" part to $v0
    la $a0, isContinue 
    syscall
    

# Ask for a number input
    li $v0, 5 # load the number user input
    syscall
    beq $v0, 1, main # if $v0 = 1 --> main


# Bye Part -> exit the program and print bye which store in Bye varible
    li $v0, 4
    la $a0, Bye
    syscall
    li $v0, 10
    syscall


# The varible datas
    .data
ask: .asciiz "Enter a number: "
binary: .asciiz "\nBinary: "
quaternary: .asciiz "\nQuaternary: "
octal: .asciiz "\nOctal: "
isContinue: .asciiz "\nContinue?(1=Yes/0=No) "
number: .asciiz "Input number is "
Bye: .asciiz "Bye!"
