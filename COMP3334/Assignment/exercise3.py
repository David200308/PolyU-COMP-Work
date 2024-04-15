## COMP3334 - Assignment
## Exercise 3
## JIANG GUANLIN (21093962D)

def pkcs(plaintext, length):
    ## If the length > block size, throw an exception
    if (length > 255):
        raise Exception("Invalid block size")
    
    paddingLength = length - (len(plaintext) % length)

    ## convert the padding length to bytes and pad the text
    paddingBytes = paddingLength.to_bytes(1, 'big')
    paddedText = plaintext.encode() + paddingBytes * paddingLength

    return paddedText


def validate_pkcs(plaintext, length):
    if (len(plaintext) % length != 0):
        raise Exception("Invalid padding")
    
    paddingLength = plaintext[-1]
    if paddingLength > length or paddingLength == 0:
        raise Exception("Invalid padding")
    
    ## Get the padding bytes from the end of the padded text
    paddingBytes = plaintext[-paddingLength : ]

    ## Set up the flage of invalid padding, if true, raise an exception
    invalidFlag = False
    for paddingByte in paddingBytes:
        if paddingByte != paddingLength:
            invalidFlag = True
            break
    if invalidFlag:
        raise Exception("Invalid padding")
    
    ## Remove the padding bytes from the padded text and return the unpadded string
    unpaddedText = plaintext[ : -paddingLength]
    return unpaddedText.decode()


def main():
    plaintext = "YELLOW SUBMARINE"
    length = 20

    ex3_1_Result = pkcs(plaintext, length)
    ex3_2_Result = validate_pkcs(ex3_1_Result, length)

    print("Padded:", ex3_1_Result)
    print("Unpadded:", ex3_2_Result)


main()
