package com.soprasteria;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main {
	
	
	public static void main(String[] args) {
		int dim = 15000;
		int vect[] = new int[dim];
		NumberFormat formatter = new DecimalFormat("#0.00000");
		final double convertion = 1000000000;
		
		System.out.println("Algo: ");
		fillVect(vect,dim);
		printVect(vect, dim);
		
		//Order Vector Time
		final long startOrder = System.nanoTime();
		mergeSort(vect, dim);
		final long durationOrder = System.nanoTime() - startOrder;
		printVect(vect, dim);
		System.out.println("Duration Order: " + formatter.format(durationOrder / convertion));
		
		//Binary Search Time
		final long startTimeBinary = System.nanoTime();
		System.out.print("The probability you wanted is (binary search): " + formatter.format(monteCarloBinary(vect, dim)) + "%");
		final long durationBinary = System.nanoTime() - startTimeBinary;
		System.out.println("     Duration: " + formatter.format(durationBinary / convertion));
		
		//Linear Search Time
		final long startTimeLinear = System.nanoTime();
		System.out.print("The probability you wanted is (linear search): " + formatter.format(monteCarloLinear(vect, dim)) + "%");
		final long durationLinear = System.nanoTime() - startTimeLinear;
		System.out.println("     Duration: " + formatter.format(durationLinear / convertion));
	}
	
	public static void fillVect(int[] vect, int dim) {
		for(int i = 0; i < dim; ++i) {
			vect[i] = (int)(Math.random()*15000);
		}
	}
	
	public static void orderVect(int[] vect, int counter, int dim) {
		if(counter == dim) {
			return;
		}
		int min = vect[counter];
		int indexMin = counter;
		for(int i = counter; i < dim; i++) {
			if(vect[i] < min) {
				min = vect[i];
				indexMin = i;
			}
		}
		vect[indexMin] = vect[counter];
		vect[counter] = min;
		orderVect(vect,++counter,dim);
	}
	
	public static void printVect(int[] vect, int dim) {
		for(int i = 0; i < dim; i++) {
			System.out.print(vect[i] + " ");
		}
		System.out.println();
	}
	
	public static boolean binarySearch(int[] vect, int dim, int numberToSearch, int counter, int originalDim) {
		if(dim < 0 || dim >= vect.length - 1) {
			return false;
		}
		else if(vect[dim] < numberToSearch && vect[dim + 1] > numberToSearch) {
			return false;
		}
		else if(vect[dim] < numberToSearch) {
			++counter;
			int exp = (int) Math.pow(2,counter);
			int term = originalDim/exp;
			if(term == 0) {
				term = 1;
			}
			return binarySearch(vect,dim + term , numberToSearch,counter,originalDim);
		}
		else if (vect[dim] > numberToSearch) {
			++counter;
			int exp = (int) Math.pow(2,counter);
			int term = originalDim/exp;
			if(term == 0) {
				term = 1;
			}
			return binarySearch(vect, dim - term, numberToSearch,counter,originalDim);
		}
		return true;
	}
	
	public static double monteCarloBinary(int[] vect, int dim) {
		double numberOfSimulations = 5000000;
		int counter;
		int numberOfTrue = 0;
		for(int i = 0; i < numberOfSimulations; ++i) {
			counter = 0;
			int randNumberToFind = (int)(Math.random() * 15000);
			if(binarySearch(vect, dim/2, randNumberToFind, counter, dim/2)) {
				++numberOfTrue;
			}
		}
		return (numberOfTrue*100)/numberOfSimulations;
	}
	
	public static boolean linearSearch(int[] vect, int numberToFind) {
		for(int i = 0; i < vect.length; i++) {
			if(vect[i] == numberToFind) {
				return true;
			}
		}
		return false;
	}
	
	public static double monteCarloLinear(int[] vect, int dim) {
		double numberOfSimulations = 5000000;
		int numberOfTrue = 0;
		for(int i = 0; i < numberOfSimulations; ++i) {
			int randNumberToFind = (int)(Math.random() * 15000);
			if(linearSearch(vect, randNumberToFind)) {
				++numberOfTrue;
			}
		}
		return (numberOfTrue*100)/numberOfSimulations;
	}
	
	public static void mergeSort(int[] vect, int dim) {
	    if (dim < 2) {
	        return;
	    }
	    int mid = dim / 2;
	    int[] leftVect = new int[mid];
	    int[] rightVect = new int[dim - mid];

	    for (int i = 0; i < mid; i++) {
	        leftVect[i] = vect[i];
	    }
	    for (int i = mid; i < dim; i++) {
	        rightVect[i - mid] = vect[i];
	    }
	    mergeSort(leftVect, mid);
	    mergeSort(rightVect, dim - mid);
	    merge(vect, leftVect, rightVect, mid, dim - mid);
	}
	
	public static void merge(int[] vect, int[] leftVect, int[] rightVect, int left, int right) {
	    int i = 0, j = 0, k = 0;
	    while (i < left && j < right) {
	        if (leftVect[i] <= rightVect[j]) {
	            vect[k++] = leftVect[i++];
	        }
	        else {
	            vect[k++] = rightVect[j++];
	        }
	    }
	    while (i < left) {
	        vect[k++] = leftVect[i++];
	    }
	    while (j < right) {
	        vect[k++] = rightVect[j++];
	    }
	}
}
