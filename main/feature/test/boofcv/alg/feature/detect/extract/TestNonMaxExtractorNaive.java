/*
 * Copyright (c) 2011, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://www.boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.alg.feature.detect.extract;

import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageFloat32;
import boofcv.testing.BoofTesting;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Peter Abeles
 */
public class TestNonMaxExtractorNaive {

	/**
	 * See if it correctly ignores features which  are equal to MAX_VALUE
	 */
	@Test
	public void exclude_MAX_VALUE() {
		ImageFloat32 inten = new ImageFloat32(30, 40);

		inten.set(15, 20, Float.MAX_VALUE);
		inten.set(20,21,Float.MAX_VALUE);
		inten.set(10,25,Float.MAX_VALUE);
		inten.set(11,24,10);
		inten.set(25,35,10);


		QueueCorner foundList = new QueueCorner(100);
		NonMaxExtractorNaive alg = new NonMaxExtractorNaive(2, 0.6F);
		// find corners the first time
		alg.process(inten,foundList);

		// only one feature should be found.  The rest should be MAX_VALUE or too close to MAX_VALUE
		assertEquals(1, foundList.size);
		assertEquals(25, foundList.data[0].x);
		assertEquals(35,foundList.data[0].y);
	}

	/**
	 * See if it produces the correct answers after adjusting the width
	 */
	@Test
	public void testRegionWidth() {
		float inten[] = new float[]{0, 1, 0, 0, 3, 4, 4, 0, 0,
				1, 0, 2, 0, 5, 0, 0, 0, 1,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 9, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 1, 0,
				0, 0, 0, 4, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0};
		ImageFloat32 img = new ImageFloat32(9, 8);
		img.data = inten;

		BoofTesting.checkSubImage(this, "testRegionWidth", true, img);
	}

	public void testRegionWidth(ImageFloat32 img) {
		QueueCorner corners = new QueueCorner(100);

		NonMaxExtractorNaive extractor;
		extractor = new NonMaxExtractorNaive(1, 0);
		extractor.process(img, corners);
		assertEquals(5, corners.size());

		corners.reset();
		extractor.setMinSeparation(2);
		extractor.process(img, corners);
		assertEquals(1, corners.size());

		corners.reset();
		extractor.setMinSeparation(3);
		extractor.process(img, corners);
		assertEquals(1, corners.size());

		assertEquals(4, corners.get(0).x);
		assertEquals(3, corners.get(0).y);
	}

	/**
	 * Make sure it does the threshold thing correctly
	 */
	@Test
	public void testThreshold() {
		float inten[] = new float[]{0, 1, 0, 0, 3, 4, 4, 0, 0,
				1, 0, 2, 0, 5, 0, 0, 0, 1,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 9, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 4, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0};
		ImageFloat32 img = new ImageFloat32(9, 8);
		img.data = inten;

		BoofTesting.checkSubImage(this, "testThreshold", true, img);
	}

	public void testThreshold(ImageFloat32 img) {
		QueueCorner corners = new QueueCorner(100);

		NonMaxExtractorNaive extractor;
		extractor = new NonMaxExtractorNaive(0, 0);
		extractor.process(img, corners);
		assertEquals(9 * 8, corners.size());

		corners.reset();
		extractor.setThresh(3);
		extractor.process(img, corners);
		assertEquals(6, corners.size());
	}
}