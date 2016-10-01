import java.lang.*;
import java.math;

public class EncryptionClass {

    public static class KeyGeneration {
        private int kSize;
        private BigInteger[] CArray; //ck = 1 and ci has (L-i) bits binary length for i = 1, ..., L-i
        private BigInteger[] bi; //bi = 2^(i-1)*ci for i = 1, ..., k
        private BigInteger sumBi;
        private BigInteger M;  //M > bi
        private BigInteger w;  //0 < w < M && gcd(M, w) = 1
        private BigInteger[] ai; //ai = bi*w mod M for all i = 1, ..., k

        //Very big primes
        private BigInteger p;
        private BigInteger q;

        private BigInteger n; //p*q
        private BigInteger phiN; // = (p-1)*(q-1)

        private BigInteger e; //1 < e < phi(n) and gcd(phi(n),e) = 1
        private BigInteger d; //1 < d < phi(n), e * d = 1 % phi(n)

        private BigInteger randomWithinRange(BigInteger min, BigInteger max) {
            BigInteger range = (max-min)+1;
            return (BigInteger)(Math.random()*range) + min;
        }

        public static BigInteger commputeBi(BigInteger i) {
            return Math.pow(2, (i-1));
        }

        public static KeyGeneration(int kSize) {
            this.kSize = kSize;
            int[] cArray = new BigInteger[kSize];
            int[] bArray = new BigInteger[kSize];
            int[] aArray = new BigInteger[kSize];
        }

        private static int

        public static void createKey(BigInteger[] privateKey, BigInteger[] publicKey) {
            // 1. randomly chooose k odd positive integers c1-ck
            for(unsigned i=0; i<kSize-1; i++) {
                BigInteger tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                while(tempNum%2 == 0) {
                    tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                }
                cArray[i] = tempNum;
            }

            cArray[kSize-1] = 1;

            // 2. compute b1
            for (unsigned i=0; i<kSize; i++) {
                BigInteger temp = computeBi(i);
                bArray[i] = temp;
                sumBi += temp;
            }

            // 3. randomly choose a modulus
            do {
                M = randomWithinRange(1, Integer.MAX_VALUE);
                w = randomWithinRange(1, Integer.MAX_VALUE);
            } while (M > sumBi && 0 < w && w < M && M.gcd(w) == 1);


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
