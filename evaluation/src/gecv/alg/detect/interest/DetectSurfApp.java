/*
 * Copyright 2011 Peter Abeles
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package gecv.alg.detect.interest;

import gecv.abst.detect.extract.FactoryFeatureFromIntensity;
import gecv.abst.detect.extract.FeatureExtractor;
import gecv.alg.misc.PixelMath;
import gecv.alg.transform.ii.IntegralImageOps;
import gecv.core.image.ConvertBufferedImage;
import gecv.gui.feature.VisualizeFeatures;
import gecv.gui.image.ShowImages;
import gecv.io.image.UtilImageIO;
import gecv.struct.image.ImageFloat32;

import java.awt.image.BufferedImage;

/**
 * Displays a window showing the selected corner-laplace features across diffferent scale spaces.
 *
 * @author Peter Abeles
 */
public class DetectSurfApp {

//	static String fileName = "evaluation/data/outdoors01.jpg";
	static String fileName = "evaluation/data/sunflowers.png";
//	static String fileName = "evaluation/data/particles01.jpg";
//	static String fileName = "evaluation/data/scale/beach02.jpg";
//	static String fileName = "evaluation/data/indoors01.jpg";
//	static String fileName = "evaluation/data/shapes01.png";

	static int NUM_FEATURES = 50;

	public static void main( String args[] ) {
		BufferedImage input = UtilImageIO.loadImage(fileName);
		ImageFloat32 inputF32 = ConvertBufferedImage.convertFrom(input,(ImageFloat32)null);

		PixelMath.divide(inputF32,inputF32,255.0f);
		ImageFloat32 integral = IntegralImageOps.transform(inputF32,null);

		FeatureExtractor extractor = FactoryFeatureFromIntensity.create(1,0.0004f,5,false,false,false);
		SurfFeatureDetector det = new SurfFeatureDetector(extractor);

		long before = System.currentTimeMillis();
		det.detect(integral);
		long after = System.currentTimeMillis();

		System.out.println("total features found: "+det.getFoundPoints().size());
		System.out.println("Time: "+(after-before));

		VisualizeFeatures.drawScalePoints(input.createGraphics(),det.getFoundPoints(),2.5);


		ShowImages.showWindow(input,"Found Features");
		System.out.println("Done");
	}
}