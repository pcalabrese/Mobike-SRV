package utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypter {
	
	private byte[] key = "749b67ba749b67ba".getBytes();
	private byte[] iv = "7ebae6fa7ebae6fa".getBytes();
	private SecretKeySpec secretKey = new SecretKeySpec(this.key, "AES");
	private IvParameterSpec ivSpec = new IvParameterSpec(this.iv);
	
	public Crypter() {}
	
	public String encrypt(String plainText) throws Exception{
		Cipher cipher;
		byte[] plainTextByte = plainText.getBytes();
		byte[] encryptedByte = null;
		cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);
		encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
	
	public String decrypt(String encryptedText) throws Exception {
		Cipher cipher;
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivSpec);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
	
	

}
