// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.CoralArmConstants;
import frc.robot.Constants.CoralRollerConstants;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.CoralArmSubsystem;
import frc.robot.subsystems.CoralRollerSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
  private DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private CoralArmSubsystem m_coralArmSubsystem = new CoralArmSubsystem();
  private CoralRollerSubsystem m_coralRollerSubsystem = new CoralRollerSubsystem();
  private CommandXboxController m_controller = new CommandXboxController(0);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_controller.povUp().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMaxSetpoint)));
    m_controller.povLeft().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMidSetpoint)));
    m_controller.povDown().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMinSetpoint)));
    m_controller.leftBumper().onTrue(new InstantCommand(() -> m_coralArmSubsystem.setDesiredAngle(CoralArmConstants.kStartingAngle)));
    m_controller.rightBumper().onTrue(new InstantCommand(() -> m_coralArmSubsystem.setDesiredAngle(CoralArmConstants.kBackMaxAngle)));
    m_controller.x().whileTrue(new RunCommand(() -> m_coralRollerSubsystem.setDesiredSpeed(CoralRollerConstants.kIntakeSpeed)));
    m_controller.y().whileTrue(new RunCommand(() -> m_coralRollerSubsystem.setDesiredSpeed(CoralRollerConstants.kEjectSpeed)));
    m_coralRollerSubsystem.setDefaultCommand(new RunCommand(() -> m_coralRollerSubsystem.setDesiredSpeed(0.0), m_coralRollerSubsystem));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  public Command getTeleopCommand() {
    return m_driveSubsystem.driveCommand(() -> MathUtil.applyDeadband(-m_controller.getLeftY(), 0.1),
        () -> MathUtil.applyDeadband(m_controller.getLeftX(), 0.1),
        () -> MathUtil.applyDeadband(m_controller.getRightX(), 0.1));
  }
}
