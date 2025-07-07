// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public final class Constants {
    public static final class DriveConstants {
        public static final int kFrontLeftPort = 1; // neo
        public static final int kFrontRightPort = 2; // neo
        public static final int kBackLeftPort = 3; // neo
        public static final int kBackRightPort = 4; // neo
    }
    public static final class ElevatorConstants {
        public static final int kElevatorPort = 7; // neo
        public static final double kElevatorP = 0.15;
        public static final double kElevatorI = 0.0;
        public static final double kElevatorD = 0.01;
        public static final double kMinSetpoint = 0.0;
        public static final double kMidSetpoint = 15.0;
        public static final double kMaxSetpoint = 25.5;
    }

}
