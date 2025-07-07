package frc.robot;

public final class Constants {
    public static final class CANIDs {
        public static final int ELEVATOR_MOTOR = 1;
    }

    public static final class ElevatorConstants {
        public static final double MAX_HEIGHT = 2.0; // m
        public static final double MIN_HEIGHT = 0.0; // m
        public static final int GEAR_RATIO = 5;
        public static final double SPROCKET_RADIUS = 0.01905; // m

        public static final double SOURCE_HEIGHT = 0.0; // m
        public static final double L1_HEIGHT = 0.5; // m
        public static final double L2_HEIGHT = 1.0; // m
        public static final double L3_HEIGHT = 1.5; // m

        public static final double TOLERANCE = 0.005; // m (5 mm tolerance)

        public static final double P = 2;
        public static final double I = 0.0;
        public static final double D = 0.0;
    }
}
