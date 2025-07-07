// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;

public class RobotContainer {
	Elevator elevator = new Elevator();
	CommandXboxController driverController = new CommandXboxController(0);

	public RobotContainer() {
		configureBindings();
	}

	private void configureBindings() {
		// Example: Use Y button to move elevator up, A button to move down
		driverController.button(1).onTrue(Commands.runOnce(() -> elevator.setState(ElevatorState.L1), elevator));
		driverController.button(2).onTrue(Commands.runOnce(() -> elevator.setState(ElevatorState.L2), elevator));
		
		// Or use triggers for continuous movement
		// driverController.rightTrigger().whileTrue(Commands.run(() -> elevator.moveUp(), elevator));
		// driverController.leftTrigger().whileTrue(Commands.run(() -> elevator.moveDown(), elevator));
	}

	public Command getAutonomousCommand() {
		return Commands.print("No autonomous command configured");
	}
}
