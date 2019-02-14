# Require correct version of cmake
cmake_minimum_required (VERSION 3.5.1)

# Set CMake CUDA compiler
set(CMAKE_CUDA_COMPILER  /usr/local/cuda-9.0/bin/nvcc)

# Set project name
project (OrthusVision LANGUAGES CUDA CXX)

# Enable CUDA language
enable_language(CUDA)

# Include/Find/Check libraries
include(FindCUDA)
include(CheckLanguage)
find_package(OpenCV REQUIRED)
find_package(CUDA REQUIRED)
check_language(CUDA)

# Set flags for C++ standard libraries
if(NOT DEFINED CMAKE_CUDA_STANDARD)
    set(CMAKE_CUDA_STANDARD 11)
    set(CMAKE_CUDA_STANDARD_REQUIRED ON)
endif()

# Include directories
include_directories(${OpenCV_INCLUDE_DIRS})
include_directories(${CMAKE_CUDA_TOOLKIT_INCLUDE_DIRECTORIES})
include_directories(${allwpilib/wpilibc/athena/include})
#include_directories(${orthusVision.h})
include_directories(${WPILib.h})
include_directories(/usr/local/include)
include_directories(/usr/local/lib)

# Disabling unneeded CUDA stuff
unset(CUDA_USE_STATIC_CUDA_RUNTIME CACHE)
option(CUDA_USE_STATIC_CUDA_RUNTIME OFF)

# Creating executable
add_executable(OrthusVision orthusVision.cpp)

# Link libraries to executable
target_link_libraries(OrthusVision ${OpenCV_LIBS})
target_link_libraries(OrthusVision ${allwpilib/wpilibc/athena/include})
target_link_libraries(OrthusVision ${WPILib.h})
