/*
 * Copyright (c) 2011-2012, Peter Abeles. All Rights Reserved.
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

package boofcv.struct.calib;

import georegression.geometry.RotationMatrixGenerator;
import georegression.struct.point.Vector3D_F64;
import georegression.struct.se.Se3_F64;

import java.io.Serializable;

/**
 * Calibration parameters for a stereo camera pair.  Includes intrinsic and extrinsic.
 *
 * @author Peter Abeles
 */
public class StereoParameters implements Serializable {
	// intrinsic camera parameters of left camera
	public IntrinsicParameters left;
	// intrinsic camera parameters of right camera
	public IntrinsicParameters right;
	// transform from left camera to right camera
	public Se3_F64 rightToLeft;

	public StereoParameters(IntrinsicParameters left,
							IntrinsicParameters right,
							Se3_F64 rightToLeft ) {
		this.left = left;
		this.rightToLeft = rightToLeft;
		this.right = right;
	}

	public StereoParameters() {
	}

	public IntrinsicParameters getLeft() {
		return left;
	}

	public void setLeft(IntrinsicParameters left) {
		this.left = left;
	}

	public Se3_F64 getRightToLeft() {
		return rightToLeft;
	}

	public void setRightToLeft(Se3_F64 rightToLeft) {
		this.rightToLeft = rightToLeft;
	}

	public IntrinsicParameters getRight() {
		return right;
	}

	public void setRight(IntrinsicParameters right) {
		this.right = right;
	}

	public void print() {
		double euler[] = RotationMatrixGenerator.matrixToEulerXYZ(rightToLeft.getR());
		Vector3D_F64 t = rightToLeft.getT();
		System.out.println();
		System.out.println("Left Camera");
		left.print();
		System.out.println();
		System.out.println("Right Camera");
		right.print();
		System.out.println("Right to Left");
		System.out.printf("  Euler       [ %8.3f , %8.3f , %8.3f ]\n",euler[0],euler[1],euler[2]);
		System.out.printf("  Translation [ %8.3f , %8.3f , %8.3f ]\n",t.x,t.y,t.z);
	}
}
