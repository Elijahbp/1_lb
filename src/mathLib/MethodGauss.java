package mathLib;

public class MethodGauss {
    public static void main(String[] args) {
        double[][] doubles = {
                {0,-1,0},
                {0,2,1},
                {1,-2,-1}
        };
        double[] doubles1= {
                2,2,2
        };
        method(doubles,doubles1);
    }

    public static double[] method(double[][] matrixA, double[] vectorB) {

        double h;
        double[][] GetMatrix = matrixA;
        double[] FreeK = vectorB;

        double[] vectorX = new double[FreeK.length];
        for (int i = 0; i < GetMatrix.length - 1; i++)
        {
            for (int j = i + 1; j < GetMatrix.length; j++)
            {
                h = GetMatrix[j][i] / GetMatrix[i][i];
                GetMatrix[j][i] = 0;
                for (int k = i + 1; k < GetMatrix[1].length; k++)
                {
                    GetMatrix[j][k] = GetMatrix[j][k] - h * GetMatrix[i][k];
                }
                FreeK[j] = FreeK[j] - h * FreeK[i];
            }
        }
        vectorX[FreeK.length - 1] = FreeK[FreeK.length - 1] / GetMatrix[GetMatrix.length - 1][GetMatrix[1].length - 1];
        for (int i = FreeK.length - 1; i >= 0; i--)
        {
            double S = 0;
            for (int j = i + 1; j < FreeK.length; j++)
            {
                S += GetMatrix[i][j] * vectorX[j];
            }
            vectorX[i] = (FreeK[i] - S) / GetMatrix[i][i];
            System.out.printf("X{ %d }={ %s }", i, vectorX[i]);
        }
        return vectorX;
    }
}
