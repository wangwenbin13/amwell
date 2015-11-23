package com.amwell.commons;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Buffer {
	public static String s_buffer = "0}[ST515k9n(2wTVw4sG82pyUr]9^bO@s51va7P0}G#*b9BY4iZ=qjI.[SAG<x$/7q/TjE5Nh1'48QGPWIUV/fWc]W8v$-)_Xs6rVYZ=#*(k515k7q)_XhwcNO@YZ=#*(k$GB82pyUHv6u/fE{'1'915klmD.KLMjan@#Ymj9r<Zx$/.-6op+|It0OPdeQ>{w5F&IGGG|<]A%)_NR+de}[2pytbWYZ!@ghiv]AN6u/fVva78QChwcNO@YZ=#*(k$GB82pyUHv6u/fE{'1'915klmD.KLMjan@#Ymj9r<Zx$/.-6op+|It0OPdeQ>{w5F&IGGcV]A7q)_XhwcVG|<]A%)_NR+de}[2pyc]Aq%^biVW4sUGP0}[S|<x$/.-NO@bChiVW489r]AE{'GP0}[STjIGs515k7q)_XtbWYZ!@ghiDE>fu#BG%^&*()_KCDEx2w82pyUHCCfEu{*(36XzBXrz,Rgo-=!STUH78=k$%^DE>fu3+|ItXh%^biUWiTE89r]AE{'4s{A'GP0}[S48NO@{'WkG(k$GB|rz,Rgo-=!STUH78=#BG%^&*(36XzB3pquNO@V[Sl)_GP0}F!;c0OjIHv{+aFU4elKL,;}[(k$qjasdeQ>aFoyF&KCDEx2f%^;c#Ymu34xoyF&*.<ZMd>yzBQdeQ>Vv]AN6GsPwl3+|It0OPgo-=!STMnHClK)_XtbWz,RLMn,;ghimD7^&*rsRL";

	/**
	 * 
	 * @param strBuffer
	 * @param strNo
	 * @return
	 */
	public static String MD5Buffer(String strBuffer, String strNo) {
		String strMD5 = "";
		String strTmp;
		int iLen = s_buffer.length() - 30;
		int iNo = Integer.parseInt(strNo);
		int iPos = iNo % iLen;
		strTmp = strBuffer + s_buffer.substring(iPos);
		return DigestUtils.md5Hex(strTmp);
	}

}
