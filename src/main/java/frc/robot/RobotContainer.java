// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {
  private DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private CommandXboxController m_controller = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public Command getTeleopCommand() {
    return m_driveSubsystem.driveCommand(() -> -m_controller.getLeftY(), () -> -m_controller.getRightX());
  }
}
