package edu.bowdoin.vhan.linearalgebra;

public class Matrix {
	private int row;
	private int column;
	private boolean isSquare;
	private double[][] entries;

	public Matrix(int rows, int columns) {
		if (rows <= 0 || columns <= 0) {
			throw new RuntimeException();
		}

		this.row = rows;
		this.column = columns;

		this.entries = new double[rows][columns];

		this.isSquare = (rows == columns);
	}

	public Matrix(double[][] entries) {
		// check if every row has the same number of entries
		int test = entries[0].length;

		for (int i = 0; i < entries.length; i++) {
			if (entries[i].length != test) {
				throw new RuntimeException();
			}
		}

		this.column = test;
		this.row = entries.length;

		this.isSquare = (this.column == this.row);

		this.entries = entries;
		
		
	}

	public int getNumberOfRows() {
		return row;
	}

	public int getNumberOfColumns() {
		return column;
	}

	public double[][] getEntries() {
		return this.entries;
	}

	public double[] getRow(int row) {
		if (row > this.row) {
			throw new RuntimeException();
		}

		return entries[row - 1];
	}

	public double getEntry(int row, int column) {
		return entries[row - 1][column - 1];
	}

	public double[] getColumn(int column) {
		double[] col = new double[this.row];

		for (int i = 1; i <= this.row; i++) {
			col[i - 1] = this.getEntry(i, column);
		}

		return col;
	}

	public void print() {
		for (int i = 1; i <= this.row; i++) {
			for (int j = 1; j <= this.column; j++) {
				System.out.print(this.getEntry(i, j) + " ");
			}
			
			System.out.print("\n");
		}
	}
	
	public void setEntry(int row, int column, double entry) {
		if (row > this.row || column > this.column) {
			throw new RuntimeException();
		}
		
		
		this.entries[row-1][column-1] = entry;
	}
	
	public boolean isSquare() {
		return this.isSquare;
	}
}
