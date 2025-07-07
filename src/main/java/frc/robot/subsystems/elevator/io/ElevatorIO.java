package frc.robot.subsystems.elevator.io;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import frc.robot.Constants;

public interface ElevatorIO {

    @AutoLog
    public static class ElevatorIOInputs implements LoggableInputs{
        public double positionMeters = 0.0;
        public double velocityMetersPerSec = 0.0;
        public double toleranceMeters = Constants.ElevatorConstants.TOLERANCE;
        public boolean atTopLimit = false;
        public boolean atBottomLimit = false;
        @Override
        public void toLog(LogTable table) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'toLog'");
        }
        @Override
        public void fromLog(LogTable table) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'fromLog'");
        }


    }

    /** Updates the set of inputs. */
    public default void updateInputs(ElevatorIOInputs inputs) {}

    /**
     * Sets the target position for the elevator's built-in PID controller.
     * @param positionMeters The target position in meters.
     */
    public default void setPosition(double positionMeters) {}

    /**
     * Sets the elevator motor voltage directly.
     * @param volts The voltage to apply.
     */
    public default void setVoltage(double volts) {}

    /**
     * Configures the P, I, and D gains of the position controller.
     */
    public default void setPID(double p, double i, double d) {}
}