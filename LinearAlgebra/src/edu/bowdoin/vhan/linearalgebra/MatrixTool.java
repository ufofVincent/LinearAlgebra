package edu.bowdoin.vhan.linearalgebra;

public class MatrixTool {

	public static Matrix addMatrix(Matrix m1, Matrix m2) {
		// check if they have the same dimensions

		if (m1.getNumberOfColumns() != m2.getNumberOfColumns() || m1.getNumberOfRows() != m2.getNumberOfRows()) {
			throw new RuntimeException();
		}

		Matrix m = new Matrix(m1.getNumberOfRows(), m2.getNumberOfColumns());

		for (int i = 1; i <= m.getNumberOfRows(); i++) {
			for (int j = 1; j <= m.getNumberOfColumns(); j++) {
				m.setEntry(i, j, m1.getEntry(i, j) + m2.getEntry(i, j));
			}
		}

		return m;
	}

	public static Matrix multiplyByScalar(Matrix m, double scalar) {

		Matrix m2 = new Matrix(m.getNumberOfRows(), m.getNumberOfColumns());
		for (int i = 1; i <= m2.getNumberOfRows(); i++) {
			for (int j = 1; j <= m2.getNumberOfColumns(); j++) {
				m2.setEntry(i, j, m.getEntry(i, j) * scalar);
			}
		}

		return m2;
	}

	public static Matrix getMinor(Matrix m, int row, int column) {
		Matrix m2 = new Matrix(m.getNumberOfRows() - 1, m.getNumberOfColumns() - 1);

		for (int i = 1; i <= m.getNumberOfRows(); i++) {
			for (int j = 1; j <= m.getNumberOfColumns(); j++) {
				// if current entry is below deleted row and to the right of deleted column
				if (i > row && j > column) {
					m2.setEntry(i - 1, j - 1, m.getEntry(i, j));
				}
				// if current entry is below deleted row but to the left of delted column
				else if (i > row && j < column) {
					m2.setEntry(i - 1, j, m.getEntry(i, j));
				}
				// if current entry is above deleted row but to the right of deleted column
				else if (i < row && j > column) {
					m2.setEntry(i, j - 1, m.getEntry(i, j));
				} else if (i < row && j < column) {
					m2.setEntry(i, j, m.getEntry(i, j));
				}

			}
		}

		return m2;
	}

	public static double getDeterminant(Matrix m) {
		// first check if the matrix is square
		if (!m.isSquare()) {
			throw new RuntimeException();
		}

		// if the current matrix is 1x1

		if (m.getNumberOfRows() == 1) {
			return m.getEntry(1, 1);
		}

		// if the current matrix is 2x2

		if (m.getNumberOfRows() == 2) {
			return (m.getEntry(2, 2) * m.getEntry(1, 1) - m.getEntry(1, 2) * m.getEntry(2, 1));
		}

		// if the square matrix is of larger dimension:
		// cofactor expansion across first row

		double determinant = 0;

		for (int i = 1; i <= m.getNumberOfColumns(); i++) {
			determinant += m.getEntry(1, i) * Math.pow(-1, i - 1) * getDeterminant(MatrixTool.getMinor(m, 1, i));
		}

		return determinant;

	}

	public static Matrix transpose(Matrix m) {
		Matrix m2 = new Matrix(m.getNumberOfColumns(), m.getNumberOfRows());

		for (int i = 1; i <= m2.getNumberOfRows(); i++) {
			for (int j = 1; j <= m2.getNumberOfColumns(); j++) {
				m2.setEntry(i, j, m.getEntry(j, i));
			}
		}

		return m2;
	}

	public static double getCofactor(Matrix m, int row, int column) {
		if (!m.isSquare()) {
			throw new RuntimeException();
		}

		return Math.pow(-1, (row + column)) * MatrixTool.getDeterminant(MatrixTool.getMinor(m, row, column));
	}

	public static Matrix getCofactorMatrix(Matrix m) {
		if (!m.isSquare()) {
			throw new RuntimeException();
		}

		Matrix m2 = new Matrix(m.getNumberOfRows(), m.getNumberOfColumns());

		for (int i = 1; i <= m.getNumberOfRows(); i++) {
			for (int j = 1; j <= m.getNumberOfColumns(); j++) {
				m2.setEntry(i, j, MatrixTool.getCofactor(m, i, j));
			}
		}

		return m2;
	}

	public static Matrix getAdjugate(Matrix m) {
		return MatrixTool.transpose(MatrixTool.getCofactorMatrix(m));
	}

	public static Matrix getInverseMatrix(Matrix m) {
		if (!m.isSquare()) {
			throw new RuntimeException();
		}

		if (MatrixTool.getDeterminant(m) == 0) {
			throw new RuntimeException();
		}

		return MatrixTool.multiplyByScalar(MatrixTool.getAdjugate(m), 1 / (MatrixTool.getDeterminant(m)));
	}

	public static Matrix multiplyMatrixByMatrix(Matrix m1, Matrix m2) {
		if (m1.getNumberOfColumns() != m2.getNumberOfRows()) {
			throw new RuntimeException();
		}

		Matrix m = new Matrix(m1.getNumberOfRows(), m2.getNumberOfColumns());
		double entry = 0;
		for (int i = 1; i <= m.getNumberOfRows(); i++) {
			for (int j = 1; j <= m.getNumberOfColumns(); j++) {

				for (int k = 1; k <= m1.getNumberOfColumns(); k++) {
					entry += m1.getEntry(i, k) * m2.getEntry(k, j);
				}
				m.setEntry(i, j, entry);
				entry = 0;
			}
		}

		return m;
	}

	public static Matrix exponentiateMatrix(Matrix m, int num) {
		// num has to be a positive integer
		if (num <= 0) {
			throw new RuntimeException();
		}

		for (int i = 1; i < num; i++) {
			m = MatrixTool.multiplyMatrixByMatrix(m, m);
		}

		return m;
	}

	// elementary operations

	public static Matrix switchRows(Matrix m, int row1, int row2) {
		
		if(row1 > m.getNumberOfRows() || row2 > m.getNumberOfRows()) {
			throw new RuntimeException();
		}
		
		double[][] entries = m.getEntries();

		double[] temp = entries[row1 - 1];
		entries[row1 - 1] = entries[row2 - 1];
		entries[row2 - 1] = temp;

		return new Matrix(entries);
	}

	public static Matrix scaleRow(Matrix m, int row, double scalar) {
		
		if (row > m.getNumberOfRows()) {
			throw new RuntimeException();
		}
		
		Matrix m2 = m;

		for (int i = 1; i <= m.getNumberOfColumns(); i++) {
			m2.setEntry(row, i, m.getEntry(row, i) * scalar);
		}

		return m2;
	}

	public static Matrix addRowByMultipleOfAnotherRow(Matrix m, int rowToBeAdded, int row2, double scalar) {
		if (rowToBeAdded > m.getNumberOfRows() || row2 > m.getNumberOfRows()) {
			throw new RuntimeException();
		}
		
		
		double[][] entries = m.getEntries();

		double[] row2multiplied = new double[m.getNumberOfColumns()];

		for (int i = 1; i <= m.getNumberOfColumns(); i++) {
			row2multiplied[i - 1] = m.getEntry(row2, i) * scalar;
		}

		for (int j = 1; j <= m.getNumberOfColumns(); j++) {
			entries[rowToBeAdded-1][j-1] = row2multiplied[j-1] + m.getEntry(rowToBeAdded, j);
		}
		
		return new Matrix(entries);
	}

}
