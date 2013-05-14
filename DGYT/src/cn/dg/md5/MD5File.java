package cn.dg.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5File {

    private final char[] hexDigits =
            { '0', '1', '2', '3', '4', '5', '6', '7','8', '9',
              'a', 'b', 'c', 'd', 'e', 'f'};
    private DataHandle handle;
    private MessageDigest md;
    private volatile boolean disposed = false;

    public MD5File(DataHandle handle){
        this.handle = handle;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.out.println("无法初始化，操作系统可能不支持MD5编码");
        }
    }


    public String getFileMD5(String filename){
        File f = new File(filename);
        return getFileMD5(f);
    }

    public String getFileMD5(File file){
        String result = "";
        disposed = false;
        try {
            FileInputStream in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            long len = file.length();
//            if(len < (1<<20)){ //小于1M的映射到内存
//                result = md5ByMap(channel,file.length());
//            }else{
            result = md5ByBuffer(channel,len);
//            }
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            handle.setException("文件找不到，请确认文件路径是否正确");
        } catch (IOException e){
            handle.setException("文件操作中通道异常断开，请确认在计算过程中没有对文件进行操作");
        } catch (Exception e){
            handle.setException("文件操作中异常停止，请重新计算");
        }
        return result;
    }



    private String md5ByBuffer(FileChannel channel,long len) throws IOException,Exception{
        ByteBuffer buff = ByteBuffer.allocate(1024);
        long one = (len>>>10)/100;
        long n = 0;
        int per = 0;
        handle.setProgress(per);
        while( channel.read(buff) != -1 && !disposed){
            buff.flip();
            md.update(buff);
            buff.flip();
            n++;
            if(n >= one){
                n = 0;
                per++;
                handle.setProgress(per);
//                System.out.println("完成度->"+per+"%");
            }   
        }
        channel.close();
        if(disposed){
            return "";
        }
        byte[] data = md.digest();
        return bytesToHex(data);
    }

    //关闭正在计算的过程
    public void dispose(){
        disposed = true;
    }


    /**
     * 把数据映射到内存中进行计算，
     * 优点：文件较小时计算快
     * 缺点：资源高，文件过大计算不是很快
     * 文件小于Integer.MAX_VALUE B的文件可以采用这种方式
     */
    private String md5ByMap0(FileChannel channel,long position,long len) throws IOException,Exception{
        MappedByteBuffer fileData = channel.map(FileChannel.MapMode.READ_ONLY, position, len);
        md.update(fileData);
        byte[] data = md.digest();
        return bytesToHex(data);
    }

    private String md5ByMap(FileChannel channel,long len) throws IOException,Exception{
        long position = 0;
        int max = Integer.MAX_VALUE >>> 2;
        while(len > max){
            MappedByteBuffer fileData = channel.map(FileChannel.MapMode.READ_ONLY, position, max);
            md.update(fileData);
            len = len - max;
            position = position + max;
        }
        return md5ByMap0(channel, position, len);
    }


    public String bytesToHex(byte[] bt){
        StringBuffer buffer = new StringBuffer(bt.length*2);
        for(int i=0;i<bt.length;i++){
            byteToHex(bt[i],buffer);
        }
        return buffer.toString();
    }

    private void byteToHex(byte b,StringBuffer buffer){
        char c1 = hexDigits[(b & 0xf0) >> 4];
        char c2 = hexDigits[(b & 0xf)];
        buffer.append(c1);
        buffer.append(c2);
    }
}

