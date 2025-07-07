package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.elevator.io.ElevatorIO;
import frc.robot.subsystems.elevator.io.ElevatorIO.ElevatorIOInputs;
import frc.robot.subsystems.elevator.io.ElevatorIOReal;
import frc.robot.subsystems.elevator.io.ElevatorIOSim;
import org.littletonrobotics.junction.Logger;

/**
 * The subsystem that controls the elevator mechanism. It manages state,
 * interfaces with the I/O layer, and provides a public API for commands.
 */
public class Elevator extends SubsystemBase {

    public enum ElevatorState {
        SOURCE, L1, L2, L3,
    }

    public enum ElevatorMode {
        MANUAL, AUTOMATIC,
    }

    private final ElevatorIO io;
    private final ElevatorIOInputs inputs = new ElevatorIOInputs();

    private ElevatorMode mode = ElevatorMode.AUTOMATIC;
    private ElevatorState state = ElevatorState.SOURCE;
    private double manualVoltage = 0.0;

    // Mechanism2d objects for simulation visualization
    private final Mechanism2d mechanism;
    private final MechanismRoot2d elevatorRoot;
    private final MechanismLigament2d elevatorCarriage;

    public Elevator() {
        // Select the correct I/O implementation based on the robot's mode
        io = Robot.isSimulation() ? new ElevatorIOSim() : new ElevatorIOReal();

        // Create the canvas for the mechanism visualization
        mechanism = new Mechanism2d(30, 160); // width, height in pixels
        elevatorRoot = mechanism.getRoot("ElevatorBase", 15, 0);
        elevatorCarriage = elevatorRoot.append(
            new MechanismLigament2d("Carriage", 0, 90, 8, new Color8Bit(Color.kGold))
        );
        
        // Publish the mechanism to SmartDashboard to be viewed in Glass
        SmartDashboard.putData("ElevatorSim", mechanism);
    }

    // --- PUBLIC API ---

    /**
     * Sets the control mode of the elevator.
     * @param newMode The desired control mode (MANUAL or AUTOMATIC).
     */
    public void setMode(ElevatorMode newMode) {
        this.mode = newMode;
    }

    /**
     * Sets the desired state for automatic control.
     * @param newState The desired state (e.g., L1, L2).
     */
    public void setState(ElevatorState newState) {
        this.state = newState;
        System.out.println("Elevator state set to: " + newState);
    }

    /** * Sets the voltage for manual control. This is intended to be called
     * repeatedly by a command.
     * @param voltage The voltage to apply to the motor.
     */
    public void setManualVoltage(double voltage) {
        // Prevent moving past software limits in manual mode
        if ((inputs.atTopLimit && voltage > 0.0) || (inputs.atBottomLimit && voltage < 0.0)) {
            this.manualVoltage = 0.0;
        } else {
            this.manualVoltage = voltage;
        }
    }

    /**
     * Configures the PID constants for the elevator controller.
     */
    public void configurePID(double p, double i, double d) {
        io.setPID(p, i, d);
    }

    /**
     * @return The current position of the elevator in meters.
     */
    public double getPositionMeters() {
        return inputs.positionMeters;
    }

    @Override
    public void periodic() {
        // Update the inputs from the I/O layer (real or sim)
        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);

        // Update the Mechanism2d visualization based on the current position
        // The scaling factor converts meters to a reasonable pixel length for the canvas
        elevatorCarriage.setLength(inputs.positionMeters * (100.0 / ElevatorConstants.MAX_HEIGHT)); 

        // Execute logic based on the current control mode
        switch (mode) {
            case MANUAL:
                io.setVoltage(manualVoltage);
                // Reset manual voltage each loop to ensure motor stops if command ends
                this.manualVoltage = 0.0;
                break;

            case AUTOMATIC:
                switch (state) {
                    case SOURCE: io.setPosition(ElevatorConstants.SOURCE_HEIGHT); break;
                    case L1:     io.setPosition(ElevatorConstants.L1_HEIGHT); break;
                    case L2:     io.setPosition(ElevatorConstants.L2_HEIGHT); break;
                    case L3:     io.setPosition(ElevatorConstants.L3_HEIGHT); break;
                }
                break;
        }
    }
}