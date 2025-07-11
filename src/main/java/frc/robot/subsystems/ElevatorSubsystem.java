// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
  private SparkMax m_elevatorMotor = new SparkMax(ElevatorConstants.kElevatorPort, MotorType.kBrushless);
  private PIDController m_elevatorPID = new PIDController(ElevatorConstants.kElevatorP, ElevatorConstants.kElevatorI,
      ElevatorConstants.kElevatorD);
  private double m_setpoint = ElevatorConstants.kMinSetpoint;

  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    SparkMaxConfig elevatorConfig = new SparkMaxConfig();
    elevatorConfig.smartCurrentLimit(40);
    elevatorConfig.idleMode(IdleMode.kBrake);
    elevatorConfig.inverted(false);
    m_elevatorMotor.configure(elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    double encoderValue = m_elevatorMotor.getEncoder().getPosition();
    SmartDashboard.putNumber("Elevator Height", encoderValue);
    SmartDashboard.putNumber("Elevator Setpoint", m_setpoint);
    SmartDashboard.putNumber("Elevator PID Error", m_elevatorPID.getError());
    double output = m_elevatorPID.calculate(encoderValue, m_setpoint);
    SmartDashboard.putNumber("Elevator PID Output", output);
    m_elevatorMotor.set(output);
  }

  public void setDesiredHeight(double setpoint) {
    m_setpoint = setpoint;
  }
}
