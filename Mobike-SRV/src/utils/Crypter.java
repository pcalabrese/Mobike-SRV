package utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import utils.exception.CryptingException;

public class Crypter {
	
	private byte[] key = "749b67ba749b67ba".getBytes();
	private byte[] iv = "7ebae6fa7ebae6fa".getBytes();
	private SecretKeySpec secretKey = new SecretKeySpec(this.key, "AES");
	private IvParameterSpec ivSpec = new IvParameterSpec(this.iv);
	
	public Crypter() {}
	
	public String encrypt(String plainText) throws CryptingException{
		Cipher cipher;
		byte[] plainTextByte = plainText.getBytes();
		byte[] encryptedByte = null;
		try {
			cipher = Cipher.getInstance("AES/CTR/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new CryptingException(e.getMessage());
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new CryptingException(e.getMessage());
		}
		try {
			encryptedByte = cipher.doFinal(plainTextByte);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptingException(e.getMessage());
		}
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}
	
	public String decrypt(String encryptedText) throws CryptingException{
		Cipher cipher;
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		try {
			cipher = Cipher.getInstance("AES/CTR/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new CryptingException(e.getMessage());
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new CryptingException(e.getMessage());
		}
		byte[] decryptedByte;
		try {
			decryptedByte = cipher.doFinal(encryptedTextByte);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new CryptingException(e.getMessage());
		}
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
	
	

}
