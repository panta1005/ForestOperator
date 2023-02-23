/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

/**
 *
 * @author Paolo
 */
public class UtilityFunctions {
    /**
	 * This method allows to calculate the binomial coefficient
	 * 
	 * @param n the integer n
	 * @param i the integer i
	 * @return 1 or binomialCoefficient result
         */
    public static float binomialCoefficient(int n, int i) {
	if (n == i || i == 0) {
		return 1;
	} else {
            float nFactorial = factorial(n);
            float iFactorial = factorial(i);
            int difference = n - i;
            float differenceFactorial = factorial(difference);
            return nFactorial / (iFactorial * differenceFactorial);
		}
	}
    /**
	 * This method allows to calculate the factorial value
	 * 
	 * @param n the number
	 * @return factorial the factorial value
    */
    public static float factorial(int n) {
	float factorial = 1;
	for (int i = n; i > 1; i--) {
            factorial = factorial * i;
	}
		return factorial;
	}
    
}
