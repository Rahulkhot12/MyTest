package com.mckesson.mhs.interop.compression;

import java.io.IOException;
import java.util.Arrays;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4SafeDecompressor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCompressionUtil {

	private static final Logger LOG = LoggerFactory.getLogger("bw.logger");

	private static final String DEFAULT_COMPRESSION_TYPE = "HIGH";
	private static final String DEFAULT_DECOMPRESSION_TYPE = "SAFE";
	private static LZ4Factory factory = LZ4Factory.fastestInstance();
	private static LZ4Compressor highCompressor = factory.highCompressor(17);
	private static LZ4Compressor fastCompressor = factory.fastCompressor();
	private static LZ4SafeDecompressor safeDecompressor = factory.safeDecompressor();

	/**
	 * Compress the input string based on the compression type
	 * 
	 * @param data
	 * @param compressionType
	 * @param isCompressionEnabled
	 * @return byte[] compressed data
	 * @throws IOException
	 */
	public static byte[] compress(String data, String compressionType, boolean isCompressionEnabled) throws IOException {
		LOG.info("Invoking data...");
		
		if(!isCompressionEnabled){
			return data.getBytes();
		}
		
		byte[] compresseddata = null;
		if (data != null) {

			if (compressionType == null || compressionType.equalsIgnoreCase(DEFAULT_COMPRESSION_TYPE)) {
				compresseddata = lZ4HighCompress(factory, data);
			} else {
				compresseddata = lZ4Fastcompress(factory, data);
			}
			LOG.info("Compressed size = " + compresseddata.length / 1024 + " kb");
			LOG.info("Exiting compress....");
		}

		return compresseddata;
	}

	/**
	 * Uncompress the input byte array to String.
	 * 
	 * @param compressedData
	 * @param deCompressionType
	 * @param isCompressionEnabled
	 * @return String
	 * @throws IOException
	 */
	public static String deCompress(byte[] compressedData, String deCompressionType, boolean isCompressionEnabled) throws IOException {
		
		LOG.error("Invoking decompress...$$$$"+isCompressionEnabled);
		
		if(!isCompressionEnabled){
			LOG.error("Calling deCompress...."+ new String(compressedData));
			return new String(compressedData);
		}
		
		
		String deCompresseddata = null;
		if (compressedData != null) {
			LOG.info("Original Compressed input data size = " + (compressedData.length / 1024) + " kb");
			if (deCompressionType == null || deCompressionType.equalsIgnoreCase(DEFAULT_DECOMPRESSION_TYPE)) {
				deCompresseddata = lZ4SafeDecompress(factory, compressedData);
			}
		}

		LOG.info("Exiting decompress.....");
		return deCompresseddata;
	}

	private static byte[] lZ4HighCompress(LZ4Factory factory, String unCompressedData) throws IOException {
		byte[] originalInput = unCompressedData.getBytes();
		int originalInputLength = originalInput.length;
		int maxCompressedLength = highCompressor.maxCompressedLength(originalInputLength);

		byte[] compressedData = new byte[maxCompressedLength];
		int compressedSize = highCompressor.compress(originalInput, 0, originalInputLength, compressedData, 0, maxCompressedLength);
		compressedData = Arrays.copyOfRange(compressedData, 0, compressedSize);
		LOG.info("Original length = " + originalInput.length);
		LOG.info("Original size before compressing = " + (originalInput.length / 1024) + " kb");
		return compressedData;
	}

	private static byte[] lZ4Fastcompress(LZ4Factory factory, String unCompressedData) throws IOException {
		byte[] originalInput = unCompressedData.getBytes();
		int originalInputLength = originalInput.length;
		int maxCompressedLength = fastCompressor.maxCompressedLength(originalInputLength);

		byte[] compressedData = new byte[maxCompressedLength];
		int compressedSize = fastCompressor.compress(originalInput, 0, originalInputLength, compressedData, 0, maxCompressedLength);
		compressedData = Arrays.copyOfRange(compressedData, 0, compressedSize);
		LOG.info("Original length = " + originalInput.length);
		LOG.info("Original size before compressing = " + (originalInput.length / 1024) + " kb");

		return compressedData;
	}

	private static String lZ4SafeDecompress(LZ4Factory factory, byte[] compressedData) throws IOException {
		byte[] restored = new byte[compressedData.length * 90];
		int uncompSize = safeDecompressor.decompress(compressedData, 0, compressedData.length, restored, 0);
		restored = Arrays.copyOfRange(restored, 0, uncompSize);
		LOG.info("Decompressed size = " + (restored.length / 1024) + " kb");
		return new String(restored);

	}
}
