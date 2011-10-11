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

package boofcv.struct.pyramid;

import boofcv.struct.image.ImageBase;

/**
 * <p>
 * Interface for updating each layer in an {@link ImagePyramid} given the original full resolution image.
 * </p>
 *
 * @author Peter Abeles
 */
public interface PyramidUpdater<T extends ImageBase, P extends ImagePyramid> {

	/**
	 * Given the original input image update the specified image pyramid
	 *
	 * @param input Original full resolution image.
	 * @param pyramid The pyramid which is to be updated.
	 */
	public void update(T input , P pyramid );
}