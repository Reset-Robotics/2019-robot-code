# Require correct version of cmake
cmake_minimum_required (VERSION 2.8)

# Set project name
project (OrthusVision LANGUAGES CXX CUDA)

# Find libraries
find_package(OpenCV REQUIRED)

# Set flags for C++ standard libraries
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

# Include directories
include_directories(${OpenCV_INCLUDE_DIRS})
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
