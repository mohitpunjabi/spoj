      import java.io.*;


	  class StdioOutput {
                private static final int BUFSIZ = 4096;
                private FileOutputStream fos;
                private byte buf[] = new byte[BUFSIZ];
                private int left;
                private int pos;
                private static boolean need_cr = false;
        
                // figure out whether we have \r or \r\n
        
                static {
                        String s = System.getProperty("line.separator");
                        need_cr = (s.length() >= 1 && s.charAt(0) == '\r');
                }
        
                // open a file
        
                public StdioOutput(String fn) throws IOException
                {
                        fos = new FileOutputStream(fn);
                        left = BUFSIZ;
                        pos = 0;
                }
        
                // open standard output
        
                public StdioOutput() throws IOException
                {
                        fos = new FileOutputStream(FileDescriptor.out);
                        left = BUFSIZ;
                        pos = 0;
                        need_cr = false;
                }
        
                // close a file
        
                public synchronized void close() throws IOException
                {
                        flush();
                        fos.close();
                        fos = null;
                }
        
                // flush output
        
                public synchronized void flush() throws IOException
                {
                        if (pos > 0)
                                fos.write(buf, 0, pos);
                        left = BUFSIZ;
                        pos = 0;
                }
        
                // output a character
        
                public synchronized void putc(int c) throws IOException
                {
        
                        // flush output buffer if needed
        
                        if (left <= 0)
                                flush();
        
                        // handle simple case
        
                        if (c != '\n' || !need_cr) {
                                left--;
                                buf[pos++] = (byte)c;
                        }
        
                        // handle \r\n
        
                        else {
                                left--;
                                buf[pos++] = '\r';
                                if (left <= 0)
                                        flush();
                                left--;
                                buf[pos++] = '\n';
                        }
                }
        
                // output a line
        
                public synchronized void putline(String s) throws IOException
                {
                        int len = (s == null ? 0 : s.length());
        
                        // empty string
        
                        if (len < 1)
                                return;
        
                        // whole string will fit in buffer
        
                        if (len + 1 <= left) {
                                if (len >= 2) {
                                        s.getBytes(0, len - 1, buf, pos);
                                        pos += len - 1;
                                        left -= len - 1;
                                }
                                putc(s.charAt(len - 1));
                        }
        
                        // whole string won't fit, do a character at a time
        
                        else {
                                for (int i = 0; i < len; i++)
                                        putc(s.charAt(i));
                        }
                }
        }
