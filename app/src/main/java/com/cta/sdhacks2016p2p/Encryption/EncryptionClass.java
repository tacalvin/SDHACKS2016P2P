import java.lang.*;
import java.math.*;

public class EncryptionClass {

    public EncryptionClass()
    {}

    public class KeyGeneration {
        private int kSize;
        private BigInteger[] cArray; //ck = 1 and ci has (L-i) bits binary length for i = 1, ..., L-i
        private BigInteger[] bArray; //bi = 2^(i-1)*ci for i = 1, ..., k
        private BigInteger sumBi;
        private BigInteger M;  //M > bi
        private BigInteger w;  //0 < w < M && gcd(M, w) = 1
        private BigInteger[] aArray; //ai = bi*w mod M for all i = 1, ..., k

        //Very big primes
        private BigInteger p;
        private BigInteger q;

        private BigInteger n; //p*q
        private BigInteger phiN; // = (p-1)*(q-1)

        private BigInteger e; //1 < e < phi(n) and gcd(phi(n),e) = 1
        private BigInteger d; //1 < d < phi(n), e * d = 1 % phi(n)

        private BigInteger randomWithinRange(int min, int max) {
            BigInteger range = BigInteger.valueOf((max-min)+1);
            return BigInteger.valueOf((long)(Math.random() * range.doubleValue()) + min);
        }

        public BigInteger commputeBi(BigInteger i) {
            int temp = i.intValue();
            return BigInteger.valueOf((long)Math.pow(2, (temp-1)));
        }

        public KeyGeneration(int kSize) {
            this.kSize = kSize;
            cArray = new BigInteger[kSize];
            bArray = new BigInteger[kSize];
            aArray = new BigInteger[kSize];
        }

        public void createKey(BigInteger[] privateKey, BigInteger[] publicKey) {
            // 1. randomly chooose k odd positive integers c1-ck
            for(int i=0; i<kSize-1; i++) {
                BigInteger tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                while(tempNum.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
                    tempNum = randomWithinRange(1, Integer.MAX_VALUE);
                }
                cArray[i] = tempNum;
            }

            cArray[kSize-1] = BigInteger.valueOf(1);

            // 2. compute b1
            for (int i=0; i<kSize; i++) {
                BigInteger temp = commputeBi(BigInteger.valueOf(i));
                bArray[i] = temp;
                sumBi = temp.add(sumBi);
            }

            // 3. randomly choose a modulus
            do {
                M = randomWithinRange(1, Integer.MAX_VALUE);
                w = randomWithinRange(1, Integer.MAX_VALUE);
            } while (M.compareTo(sumBi) != 1 && w.compareTo(BigInteger.valueOf(0)) != 1 &&
             w.compareTo(M) != -1 && M.gcd(w).compareTo(BigInteger.valueOf(1)) != 0);

            for (int i=0; i<kSize; i++) {
                aArray[i] = bArray[i].multiply(w).mod(M);
            }

            do {
                do {
                    p = randomWithinRange(0, Integer.MAX_VALUE);
                } while (p.isProbablePrime(1));
                do {
                    q = randomWithinRange(0, Integer.MAX_VALUE);
                } while (q.isProbablePrime(1));
            } while (p.minus(BigInteger.valueOf(1).divide(BigInteger.valueOf(2).isProbablePrime(1))) &&
            q.minus(BigInteger.valueOf(1).divide(BigInteger.valueOf(2).isProbablePrime(1))));

            n = p * q;
            phiN = (p.minus(BigInteger.valueOf(1)) * q.minus(BigInteger.valueOf(1)));

            do {
                 e = randomWithinRange(0, Integer.MAX_VALUE);
            } while (e.compareTo(BigInteger.valueOf(1)) != 1 && e.compareTo(phiN) && phiN.gcd(e) != 1);

            d = ExtendedEuclid(1, phiN.intValue());
        }


        //e.d = 1 mod phi(N)
        public BigInteger ExtendedEuclid() {
            d = 
        }
    }

    public class Encryption {
        //Sender executes the following steps

        //1 Generate pseudorandom string X = (x1,...,xk) with hamming weight h such that v = k/h be an integer
        //hamming weight h such that v = k/h be an integer


        //2 Computes ~m = (d1 || ... || dh)


        //3 Using pesudorandom string X=(x1,...,xk) performs a random permutation on the message blocks di,
        //1 <= i <= h and pad some confuse data blocks to them such that



    }





}
