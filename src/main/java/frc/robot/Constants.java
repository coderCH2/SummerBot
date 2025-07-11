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
        public static final double kElevatorD = 0.005;
        public static final double kMinSetpoint = 0.0;
        public static final double kMidSetpoint = 15.0;
        public static final double kMaxSetpoint = 25.5;
    }

    public static final class CoralRollerConstants {
        public static final int kRollerPort = 9;
        public static final double kEjectSpeed = 0.2;
        public static final double kIntakeSpeed = -0.2;
        public static final double kSensorBreakDistance = 0.0;
    }

    public static final class CoralArmConstants {
        public static final int kArmPort = 8;
        public static final double kArmP = 0.03;
        public static final double kArmI = 0.0;
        public static final double kArmD = 0.0;
        public static final double kStartingAngle = 0.0;
        public static final double kFrontHorizontalAngle = -13.35;
        public static final double kVerticalAngle = -31.65;
        public static final double kBackHorizontalAngle = -54.15;
        public static final double kBackMaxAngle = -61.90;

            //-31.5 straight up   weird back L-53.07   m-61.5
        //-31.66
        //-54.1
        // -61.9
        //
    }

}
