#!/bin/bash

# Function to display help
function display_help {
    echo "Usage: $0 [options]"
    echo "Options:"
    echo "  -h, --help       Display this help and exit"
    echo "  -c, --compile    Compile PIM Java files to the 'out' directory"
    echo "  -r, --run        Run the PIM Java program"
}

# Function to compile Java files to the 'out' directory
function compile_java {
    # Create the 'out' directory if it doesn't exist
    mkdir -p ./PIM/out/production/PIM

    # Compile Java files and place the compiled classes in the 'out' directory
    javac -d ./PIM/out/production/PIM ./PIM/src/controller/*.java ./PIM/src/model/*.java ./PIM/src/view/*.java
}

# Function to run the Java program
function run_java {
    cd ./PIM/out/production/PIM
    java controller.PIM
}

# Parse command-line options
while [[ $# -gt 0 ]]; do
    case "$1" in
        -h|--help)
            display_help
            exit 0
            ;;
        -c|--compile)
            compile_java
            exit 0
            ;;
        -r|--run)
            run_java
            exit 0
            ;;
        *)
            echo "Unknown option: $1"
            display_help
            exit 1
            ;;
    esac
    shift
done

# If no options provided, display help
if [ "$#" -eq 0 ]; then
    display_help
fi
