package edu.bowdoin.vhan.linearalgebra;


public class LinearAlgebra {

	public static void main(String[] args) {
		
		double[][] entries = {{3,2, 1,2}, {4,2, 2,3}, {4,2, 5,6}, {5,6,7,8}};
		
		Matrix m = new Matrix(entries);
		
		MatrixTool.addRowByMultipleOfAnotherRow(m, 2, 3, 2).print();
		
	}

}
