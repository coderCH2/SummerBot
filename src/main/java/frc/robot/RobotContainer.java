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
import frc.robot.Constants.AlgaeArmConstants;
import frc.robot.Constants.AlgaeRollerConstants;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.AlgaeArmSubsystem;
import frc.robot.subsystems.AlgaeRollerSubsytem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
  private DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private ElevatorSubsystem m_elevatorSubsystem = new ElevatorSubsystem();
  private AlgaeRollerSubsytem m_algaeRollerSubsystem = new AlgaeRollerSubsytem();
  private AlgaeArmSubsystem m_algaeArmSubsystem = new AlgaeArmSubsystem();
  private CommandXboxController m_controller = new CommandXboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_controller.povUp().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMaxHeight)));
    m_controller.povLeft().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMidHeight)));
    m_controller.povDown().onTrue(new InstantCommand(() -> m_elevatorSubsystem.setDesiredHeight(ElevatorConstants.kMinHeight)));
    m_controller.leftTrigger().onTrue(new InstantCommand(() -> m_algaeArmSubsystem.setDesiredAngle(AlgaeArmConstants.kMinAngle)));
    m_controller.rightTrigger().onTrue(new InstantCommand(() -> m_algaeArmSubsystem.setDesiredAngle(AlgaeArmConstants.kMaxAngle)));
    m_controller.a().whileTrue(new RunCommand(() -> m_algaeRollerSubsystem.setDesiredSpeed(AlgaeRollerConstants.kIntakeSpeed)));
    m_controller.b().whileTrue(new RunCommand(() -> m_algaeRollerSubsystem.setDesiredSpeed(AlgaeRollerConstants.kEjectSpeed)));
    m_algaeRollerSubsystem.setDefaultCommand(new RunCommand(() -> m_algaeRollerSubsystem.setDesiredSpeed(0.0), m_algaeRollerSubsystem));
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
