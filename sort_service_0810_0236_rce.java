// 代码生成时间: 2025-08-10 02:36:33
package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Comparator;
# 优化算法效率

@Service
public class SortService {

    // 冒泡排序算法实现
    public int[] bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        for (int i = 0; i < arr.length; i++) {
# 改进用户体验
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换相邻元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
# 改进用户体验
        }
# TODO: 优化性能
        return arr;
    }

    // 快速排序算法实现
    public int[] quickSort(int[] arr, int low, int high) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        if (low < high) {
            int pivotIndex = partition(arr, low, high);
# 增强安全性
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
        return arr;
# TODO: 优化性能
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
# NOTE: 重要实现细节
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
# 添加错误处理
        return i + 1;
    }

    // 插入排序算法实现
    public int[] insertionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
# 添加错误处理
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        return arr;
    }

    // 合并排序算法实现
# NOTE: 重要实现细节
    public void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, temp);
            mergeSort(arr, mid + 1, right, temp);
            merge(arr, left, mid, right, temp);
        }
    }

    private void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left, j = mid + 1, k = left;
# 扩展功能模块
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
# FIXME: 处理边界情况
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (i = left, k = left; i <= right; i++, k++) {
            arr[i] = temp[k];
        }
    }

    // 归并排序算法 - 辅助方法，用于在主方法中创建临时数组
# 改进用户体验
    public int[] mergeSort(int[] arr) {
        int[] temp = new int[arr.length];
# FIXME: 处理边界情况
        mergeSort(arr, 0, arr.length - 1, temp);
        return arr;
    }
}
