package frc.robot.subsystems.elevator.io;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants.CANIDs;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorIOReal implements ElevatorIO {
    private final SparkMax elevatorMotor;
    private final RelativeEncoder elevatorEncoder;
    private SparkClosedLoopController pidController;

    private final double encoderToMeters;

    public ElevatorIOReal() {
        encoderToMeters = (2 * Math.PI * ElevatorConstants.SPROCKET_RADIUS) / ElevatorConstants.GEAR_RATIO;

        elevatorMotor = new SparkMax(CANIDs.ELEVATOR_MOTOR, MotorType.kBrushless);
        elevatorMotor.configure(new SparkMaxConfig()
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake)
                .apply(new EncoderConfig()
                        .positionConversionFactor(encoderToMeters)
                        .velocityConversionFactor(encoderToMeters / 60.0))
                .apply(new ClosedLoopConfig()
                        .pid(ElevatorConstants.P, ElevatorConstants.I, ElevatorConstants.D)
                        .iZone(0.0)),
                ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        elevatorEncoder = elevatorMotor.getEncoder();

        pidController = elevatorMotor.getClosedLoopController();

    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.positionMeters = elevatorEncoder.getPosition();
        inputs.velocityMetersPerSec = elevatorEncoder.getVelocity();
        inputs.atBottomLimit = elevatorEncoder.getPosition() <= ElevatorConstants.MIN_HEIGHT
                + ElevatorConstants.TOLERANCE;
        inputs.atTopLimit = elevatorEncoder.getPosition() >= ElevatorConstants.MAX_HEIGHT - ElevatorConstants.TOLERANCE;
    }

    @Override
    public void setPosition(double positionMeters) {
        pidController.setReference(positionMeters, ControlType.kPosition);
    }

    @Override
    public void setVoltage(double volts) {
        elevatorMotor.setVoltage(volts);
    }

    @Override
    public void setPID(double p, double i, double d) {
        elevatorMotor.configure(new SparkMaxConfig()
                .apply(new ClosedLoopConfig()
                        .pid(p, i, d)
                        .iZone(0.0)),
                ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }
}