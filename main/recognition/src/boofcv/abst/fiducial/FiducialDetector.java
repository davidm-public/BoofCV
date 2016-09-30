/*
 * Copyright (c) 2011-2016, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
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

package boofcv.abst.fiducial;

import boofcv.struct.calib.PinholeRadial;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.se.Se3_F64;

/**
 * Interface for detecting fiducials and estimating their 6-DOF pose.  The camera must be calibrated by
 * specifying {@link PinholeRadial}.  When one or more fiducials are detected their IDs and pose
 * are returned.
 *
 * @author Peter Abeles
 */
public interface FiducialDetector<T extends ImageBase>
{
	/**
	 * Call to detect the fiducial inside the image.  Must call
	 * {@link #setIntrinsic(PinholeRadial)} first.
	 * @param input Input image.  Not modified.
	 */
	void detect( T input );

	/**
	 * <p>Computes metrics which represents the fiducial's pose estimate stability given its current observed state.
	 * This can be viewed as an estimate of the pose estimate's precision, but not its accuracy.  These numbers are
	 * generated by perturbing landmarks (by the user provided amount) and seeing how it affects
	 * the pose estimate..</p>
	 *
	 * <p>The metrics should be considered more qualitative than quantitative
	 * since exactly how this metric is computed isn't specified and can vary depending on target type of
	 * implementation type.  The results could every vary each time its called, even with the the exact same inputs.
	 * </p>
	 *
	 * @param which Index of which fiducial the stability is being requested from
	 * @param disturbance Amount of the applied disturbance, in pixels.  Try 0.25
	 * @param results (output) Storage for stability metrics.
	 * @return true if successful or false if it failed for some reason
	 */
	boolean computeStability( int which , double disturbance , FiducialStability results );

	/**
	 * Specifies the intrinsic camera parameters.  Allows for the euclidean geometry of be extracted from
	 * a single image
	 *
	 * @param intrinsic The camera's intrinsic parmeters
	 */
	void setIntrinsic( PinholeRadial intrinsic );

	/**
	 * The total number of targets found
	 * @return number of targets found
	 */
	int totalFound();

	/**
	 * Used to retrieve the transformation from the fiducial's reference frame to the camera's reference frame.
	 *
	 * @param which Fiducial's index
	 * @param fiducialToCamera (output) Storage for the transform. modified.
	 */
	void getFiducialToCamera(int which, Se3_F64 fiducialToCamera);

	/**
	 * If applicable, returns the ID of the fiducial found.
	 * @param which Fiducial's index
	 * @return ID of the fiducial
	 */
	long getId( int which );

	/**
	 * Returns the width of the fiducial in world units.  If not square then it returns a reasonable
	 * approximation.  Intended for use in visualization and not precise calculations.
	 *
	 * @param which Fiducial's index
	 * @return Fiducial's width.
	 */
	double getWidth( int which );

	/**
	 * Type of input image
	 */
	ImageType<T> getInputType();

	/**
	 * If true then the ID number feature is supported
	 * @return true if supported or false if not
	 */
	boolean isSupportedID();

	/**
	 * If true then the pose estimate is supported
	 * @return true if supported or false if not
	 */
	boolean isSupportedPose();

	/**
	 * Does the fiducial have a known size?  If the size is known then {@link #getWidth(int)}
	 * returns a meaningful number and the location can be fully resolved.
	 * @return true if it's size is known
	 */
	boolean isSizeKnown();
}
