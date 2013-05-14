package cn.dg.helper;

import java.util.StringTokenizer;

public class StringHelper {
	 /**
	    *将空字符串转换为""
	    * @param 原始字符串
	    * @return 返回转换后的字符串
	    */
	   public static final String convertStringNull(String strOrig)
	   {
	      String strReturn = "";
	      if (strOrig == null || strOrig.equals("null"))
	      {
	         strReturn = "";
	      }
	      else
	      {
	         strReturn = trim(strOrig.trim());
	      }
	      return strReturn;
	   }


	   /**
	    * 分解以特定分隔符分隔多个同一类型信息的字符串为字符串数组
	    * @param strOrigin 原始字符串
	    * @param separator 分隔符
	    * @return
	    */
	   public static final String[] parserString(String strOrigin, String separator)
	   {
	      try
	      {
	         StringTokenizer st;
	         String strItem;

	         if (strOrigin == null)
	         {
	            return null;
	         }
	         st = new StringTokenizer(strOrigin, separator);
	         String[] returnValue = new String[st.countTokens()];
	         int index = 0;
	         while (st.hasMoreTokens())
	         {
	            strItem = (String) st.nextToken();
	            if (strItem != null && strItem.trim().length() != 0)
	            {
	               returnValue[index++] = strItem;
	            }
	         }
	         return returnValue;
	      }
	      catch (Exception e)
	      {
	         return null;
	      }
	   }

	   /**
	    * 将传入的字符串转换为中文字符串，并将空字符串转换为""
	    * @param strOrigin 原始字符串
	    * @return 中文字符串
	    */
	   public static String toChineseStr(String strOrigin)
	   {
	      if (strOrigin == null || strOrigin.equals("null"))
	      {
	         strOrigin = "";
	      }
	      else
	      {
	         strOrigin = strOrigin.trim();
	      }

	      try
	      {
	         strOrigin = new String(strOrigin.getBytes("ISO8859_1"), "GBK");
	      }
	      catch (Exception e)
	      {
	      }
	      return strOrigin;
	   }

	   /**
	    * 将中文字符串转换为UTF-8编码格式，并将空字符串转换为""
	    * @param strOrigin strOrigin 原始字符串（中文字符串）
	    * @return
	    */
	   public static String toStandardStr(String strOrigin)
	   {
	      if (strOrigin == null || strOrigin.equals("null"))
	      {
	         strOrigin = "";
	      }
	      else
	      {
	         strOrigin = strOrigin.trim();
	      }

	      try
	      {
	         strOrigin = new String(strOrigin.getBytes("GBK"), "UTF_8");
	      }
	      catch (Exception e)
	      {
	      }
	      return strOrigin;
	   }

	   /**
	    *
	    * @param s
	    * @param separatorSign
	    * @return String[]
	    */

	   public static String[] split(String s, String separatorSign)
	   {
	      try
	      {
	         if (s == null)
	            return null;
	         int index = 0;
	         java.util.Vector vec = new java.util.Vector();
	         while (true)
	         {
	            index = s.indexOf(separatorSign, index);
	            if (index < 0)
	               break;
	            vec.addElement(new Integer(index++));
	         }

	         int size = vec.size();

	         if (size <= 0)
	         {
	            String[] ret = new String[1];
	            ret[0] = s;
	            return ret;
	         }

	         String[] strarr = new String[size + 1];

	         Integer[] indArr = new Integer[size];
	         vec.copyInto(indArr);

	         // sort the index array for getting the string.
	         java.util.Arrays.sort(indArr);

	         int ind = 0;
	         int len = strarr.length;
	         for (int j = 0; j < (len - 1); j++)
	         {
	            strarr[j] = s.substring(ind, indArr[j].intValue());
	            ind = indArr[j].intValue() + 1;
	         }
	         if (len > 0)
	            strarr[len - 1] = s.substring(ind);

	         return strarr;
	      }
	      catch (Exception e)
	      {
	         return null;
	      }
	   }
	 /**
	    *将空格串" "或空指针转换为html的空格编码
	    * @param 原始字符串
	    * @return 返回转换后的字符串
	    */
	   public static  final String filterNullStringToHTMLSpace(String strOrigin)
	   {
	      String rets = "";
	      if (strOrigin == null)
	      {
	         rets= "&nbsp;";
	      }
	      else if (strOrigin.length() == 0)
	      {
	         rets = "&nbsp;";
	      }
	      else
	      {

	        for(int i=0;i<strOrigin.length();i++)
	        {
	          if (strOrigin.charAt(i)==' ') {
	            rets +=  "&nbsp;";

	          }
	          else {
	            rets += strOrigin.charAt(i);

	          }
	        }

	      }
	      return rets;
	   }

	   /**
	    * 将数字0转换为""，并将空字符串转换为""
	    * @param strOrigin strOrigin 原始字符串（中文字符串）
	    * @return
	    */
	   public static String convertZeroToSpace(String strOrigin)
	   {
	      if (strOrigin == null || strOrigin.equals("null") || strOrigin.equals("0"))
	      {
	         strOrigin = "";
	      }
	      else
	      {
	         strOrigin = strOrigin.trim();
	      }

	      return strOrigin;
	   }

	   public static String trim(String s)
	   {
	     try {
	         s= s.trim();
	     }catch(Exception e)
	     {
	       e.printStackTrace();
	     }
	     return s;
	   }

	 public static String toLowerCase(String strUp)
	 {
	   return strUp.toLowerCase();
	 }

	 /**
	  * 替换字符串函数
	  * @param src 被操作的字符串
	  * @param replace 被替换内容
	  * @param dest 替换内容
	  * @return
	  */
	 public static String replaceAll(String src, String replace, String dest) {
	    StringBuffer buf = new StringBuffer(src);
	    String m_dest = "";
	    if (dest != null) {
	      m_dest = dest;
	    }
	    int i = 0;
	    while ( (i = buf.indexOf(replace, i)) != -1) {
	      buf = new StringBuffer(buf.subSequence(0, i) + m_dest +
	                            buf.substring(i + replace.length()));
	      i += m_dest.length();
	    }
	    return buf.toString();
	  }




}
