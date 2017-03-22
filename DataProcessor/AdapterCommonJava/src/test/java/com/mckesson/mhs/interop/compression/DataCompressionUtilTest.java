package com.mckesson.mhs.interop.compression;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DataCompressionUtilTest {
	private static final String DEFAULT_COMPRESSION_TYPE = "High";
	private static final String DEFAULT_DECOMPRESSION_TYPE = "Safe";

	@Test
	public void testCompressDecompress() throws Exception {
		String data = new String("Test data Test data Test data");

		byte[] compresseddata = DataCompressionUtil.compress(data, DEFAULT_COMPRESSION_TYPE, true);
		String decompresseddata = DataCompressionUtil.deCompress(compresseddata, DEFAULT_DECOMPRESSION_TYPE, true);
		System.out.println(data + "===============" + decompresseddata);
		assertEquals(data, decompresseddata);
		assertEquals(data.length(), decompresseddata.length());
	}

}
