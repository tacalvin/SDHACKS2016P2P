import java.lang.*;
import java.math;

public class EncriptionClass {

    public static class KeyGeneration {
        private int kSize;
        private int[] CArray; //ck = 1 and ci has (L-i) bits binary length for i = 1, ..., L-i
        private int[] bi; //bi = 2^(i-1)*ci for i = 1, ..., k
        private int M;  //M > bi
        private int w;  //0 < w < M && gcd(M, w) = 1
        private int[] ai; //ai = bi*w mod M for all i = 1, ..., k

        //Very big primes
        private int p;
        private int q;

        private int n; //p*q
        private int phiN; // = (p-1)*(q-1)

        private int e; //1 < e < phi(n) and gcd(phi(n),e) = 1
        private int d; //1 < d < phi(n), e * d = 1 % phi(n)

        private int randomWithinRange(int min, int max) {
            int range = (max-min)+1;
            return (int)(Math.random()*range) + min;
        }

        public static commputeBi(int i) {
            return Math.pow(2, (i-1));
        }

        public static KeyGeneration(int kSize) {
            int[] cArray = new int[kSize];

            for(unsigned i=0; i<kSize-1; i++) {
                int tempNum = randomWithinRange(1, MAX_VALUE);
                while(tempNum%2 == 0) {
                    tempNum = randomWithinRange(1, MAX_VALUE);
                }
                cArray[i] = tempNum;
            }

            cArray[kSize-1] = 1;
        }












        public static int[] ExtendedEuclid(int a, int b)
        {
            int[] ans = new int[3];
            int q;

            if (b == 0)  {  /*  If b = 0, then we're done...  */
                ans[0] = a;
                ans[1] = 1;
                ans[2] = 0;
            }
            else
                {     /*  Otherwise, make a recursive function call  */
                   q = a/b;
                   ans = ExtendedEuclid (b, a % b);
                   int temp = ans[1] - ans[2]*q;
                   ans[1] = ans[2];
                   ans[2] = temp;
                }

            return ans;
        }




    }


}
