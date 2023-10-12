import os
import shutil

def move_files(file_list_path, destination_directory):
    # Check if destination directory exists, if not create it
    if not os.path.exists(destination_directory):
        os.makedirs(destination_directory)

    # Read the list of files from the provided text file
    with open(file_list_path, 'r') as file:
        file_paths = file.readlines()

    # Move each file to the destination directory
    for file_path in file_paths:
        file_path = file_path.strip()  # Remove newline and other whitespace
        if os.path.exists(file_path):
            # Construct the destination path for the file
            destination_path = os.path.join(destination_directory, os.path.basename(file_path))
            shutil.move(file_path, destination_path)
            print(f"Moved {file_path} to {destination_path}")
        else:
            print(f"File not found: {file_path}")

if __name__ == "__main__":
    # Prompt user for path of text file containing list of files to move
    file_list_path = input("Enter the path to the text file containing list of file paths: ")

    # Prompt user for destination directory
    destination_directory = input("Enter the path to the destination directory: ")

    move_files(file_list_path, destination_directory)
