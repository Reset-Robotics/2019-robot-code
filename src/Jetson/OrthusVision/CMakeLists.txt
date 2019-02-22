#=========================================================================
cmake_minimum_required(VERSION 3.5.1)
set(CMAKE_CUDA_COMPILER  /usr/local/cuda-9.0/bin/nvcc)

project(OrthusVision)
include(FindCUDA)

set(CMAKE_CXX_STANDARD 14)
find_package( OpenCV REQUIRED )
find_package(CUDA REQUIRED)
set(CUDA_NVCC_FLAGS ${CUDA_NVCC_FLAGS} -gencode arch=compute_30,code=sm_30)

include_directories( ${OpenCV_INCLUDE_DIRS} )

if(NOT DEFINED CMAKE_CUDA_STANDARD)
    set(CMAKE_CUDA_STANDARD 11)
    set(CMAKE_CUDA_STANDARD_REQUIRED ON)
endif()

include_directories(${WPILib.h})
include_directories(/usr/local/include)
include_directories(/usr/local/lib)

include_directories(${CMAKE_CUDA_TOOLKIT_INCLUDE_DIRECTORIES})
include_directories(${allwpilib/wpilibc/athena/include})
include_directories( ${OpenCV_INCLUDE_DIRS} )


unset(CUDA_USE_STATIC_CUDA_RUNTIME CACHE)
option(CUDA_USE_STATIC_CUDA_RUNTIME OFF)

add_executable(OrthusVisionTest orthusVision.cpp)

target_link_libraries(OrthusVisionTest ${OpenCV_LIBS} )
target_link_libraries(OrthusVisionTest ${OpenCV_LIBS})
target_link_libraries(OrthusVisionTest ${allwpilib/wpilibc/athena/include})
target_link_libraries(OrthusVisionTest ${WPILib.h})
#====================================================


