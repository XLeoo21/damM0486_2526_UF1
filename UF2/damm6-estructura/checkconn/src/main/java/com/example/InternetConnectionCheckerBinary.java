package src.main.java.com.example;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.net.InetAddress;

public class InternetConnectionCheckerBinary {
    private static final String LOG_FILE = "connection_log.bin";
    private static final int CHECK_INTERVAL_MS = 5000;
    private static final String TEST_HOST = "8.8.8.8"; // Google's DNS

    public static void main(String[] args) {
        if (args.length > 0 && "readlog".equalsIgnoreCase(args[0])) {
            try {
                if (args.length > 1) {
                    try {
                        int n = Integer.parseInt(args[1]);
                        readLastNLog(LOG_FILE, n);
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid number for last-N: " + args[1]);
                        System.exit(2);
                    }
                } else {
                    readLog(LOG_FILE);
                }
            } catch (IOException e) {
                System.err.println("Error reading log: " + e.getMessage());
                System.exit(3);
            }
            return;
        }

        System.out.println("Starting Internet Connection Checker (binary log mode)...");
        try (DataOutputStream logStream = new DataOutputStream(new FileOutputStream(LOG_FILE))) {
            while (true) {
                boolean isConnected = checkInternetConnection();
                long timestamp = System.currentTimeMillis();

                logStream.writeLong(timestamp);
                logStream.writeBoolean(isConnected);
                logStream.flush(); // Ensure data is written immediately

                System.out.printf("[%tF %<tT] Status: %s%n", timestamp,
                        isConnected ? "CONNECTED" : "DISCONNECTED");

                Thread.sleep(CHECK_INTERVAL_MS);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static boolean checkInternetConnection() {
        try {
            InetAddress address = InetAddress.getByName(TEST_HOST);
            return address.isReachable(3000); // Timeout in ms
        } catch (IOException e) {
            return false;
        }
    }

    public static void readLog(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            throw new IOException("Log file does not exist: " + path);
        }
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            long fileLen = raf.length();
            final int RECORD_SIZE = 9; // 8 bytes long + 1 byte boolean
            long records = fileLen / RECORD_SIZE;
            raf.seek(0);

            long ts = 0;
            boolean status = false;
            for (long i = 0; i < records; i++) {
                ts = raf.readLong();
                status = raf.readBoolean();

                String conn = status ? "CONNECTED" : "DISCONNECTED";
                if (i == 0 || status) {
                    System.out.printf("[%1$tF %1$tT] Status ", ts);
                    System.out.println(conn);
                }
            }
            System.out.printf("LAST [%1$tF %1$tT] Status ", ts);
            System.out.println(status ? "CONNECTED" : "DISCONNECTED");
        }
    }

    public static void readLastNLog(String path, int n) throws IOException {
        if (n <= 0) {
            System.out.println("No lines requested (n <= 0)");
            return;
        }
        File f = new File(path);
        if (!f.exists()) {
            throw new IOException("Log file does not exist: " + path);
        }
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            final int RECORD_SIZE = 9;
            long fileLen = raf.length();
            long totalRecords = fileLen / RECORD_SIZE;
            long toRead = Math.min(totalRecords, n);
            long startRecord = Math.max(0, totalRecords - toRead);
            raf.seek(startRecord * RECORD_SIZE);
            for (long i = 0; i < toRead; i++) {
                long ts = raf.readLong();
                boolean status = raf.readBoolean();
                System.out.printf("[%1$tF %1$tT] Status: %s%n", ts,
                        status ? "CONNECTED" : "DISCONNECTED");
            }
        }
    }
}