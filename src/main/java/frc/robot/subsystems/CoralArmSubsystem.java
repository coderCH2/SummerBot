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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class CoralArmSubsystem extends SubsystemBase {
  private SparkMax m_armMotor = new SparkMax(Constants.CoralArmConstants.kArmPort, MotorType.kBrushless);
  private PIDController m_armPID = new PIDController(Constants.CoralArmConstants.kArmP,Constants.CoralArmConstants.kArmI,Constants.CoralArmConstants.kArmD);
  private double m_setpoint = 0.0;

  /** Creates a new CoralArmSubsystem. */  
  public CoralArmSubsystem() {
    m_armMotor.getEncoder().setPosition(0);
    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(40);
    config.idleMode(IdleMode.kBrake);
    config.inverted(false);
    m_armMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    double encoderValue = m_armMotor.getEncoder().getPosition();
    SmartDashboard.putNumber("Coral Arm Angle", encoderValue);
    SmartDashboard.putNumber("Coral Arm Setpoint", m_setpoint);
    SmartDashboard.putNumber("Coral Arm PID Error", m_armPID.getError());
    double output = m_armPID.calculate(encoderValue, m_setpoint);
    SmartDashboard.putNumber("Coral Arm PID Output", output);
    m_armMotor.set(output);
  }

  public void setDesiredAngle (double input){
    m_setpoint = input;
  }


}
