package frc.robot.subsystems.elevator.io;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorIOSim implements ElevatorIO {

    private final ElevatorSim sim;
    private final PIDController pidController;

    private double appliedVolts = 0.0;
    private boolean isAuto = false; // Flag to track control mode

    public ElevatorIOSim() {
        sim = new ElevatorSim(
                DCMotor.getNEO(1),
                ElevatorConstants.GEAR_RATIO,
                5.0, // Carriage mass in kilograms (example value)
                ElevatorConstants.SPROCKET_RADIUS,
                ElevatorConstants.MIN_HEIGHT,
                ElevatorConstants.MAX_HEIGHT,
                true, // Simulate gravity
                ElevatorConstants.MIN_HEIGHT // Explicitly set starting height
        );
        pidController = new PIDController(ElevatorConstants.P, ElevatorConstants.I, ElevatorConstants.D);
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        // In automatic mode, calculate PID output and update applied voltage
        if (isAuto) {
            appliedVolts = pidController.calculate(sim.getPositionMeters()) * 12;
        }

        // Set the voltage and advance the physics simulation
        sim.setInputVoltage(appliedVolts);
        sim.update(0.020);

        // Update inputs from the simulation state
        inputs.positionMeters = sim.getPositionMeters();
        inputs.velocityMetersPerSec = sim.getVelocityMetersPerSecond();
        inputs.atBottomLimit = sim.hasHitLowerLimit();
        inputs.atTopLimit = sim.hasHitUpperLimit();

        // print in AdvantageScope
        SmartDashboard.putNumber("Elevator Applied Volts", appliedVolts);
        SmartDashboard.putNumber("Elevator Position (m)", inputs.positionMeters);
        SmartDashboard.putNumber("Elevator Velocity (m/s)", inputs.velocityMetersPerSec);
        SmartDashboard.putBoolean("Elevator At Bottom Limit", inputs.atBottomLimit);
        SmartDashboard.putBoolean("Elevator At Top Limit", inputs.atTopLimit);
    }

    @Override
    public void setPosition(double positionMeters) {
        isAuto = true; // Enable automatic (PID) mode
        pidController.setSetpoint(positionMeters);
    }

    @Override
    public void setVoltage(double volts) {
        isAuto = false; // Disable automatic mode for manual control
        appliedVolts = volts;
    }

    @Override
    public void setPID(double p, double i, double d) {
        pidController.setPID(p, i, d);
    }
}