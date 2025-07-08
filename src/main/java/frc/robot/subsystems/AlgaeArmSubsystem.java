// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlgaeArmConstants;

public class AlgaeArmSubsystem extends SubsystemBase {
  private SparkMax m_algaeArmMotor = new SparkMax(AlgaeArmConstants.kAlgaeArmPort, MotorType.kBrushless);
  private PIDController m_algaeArmPID = new PIDController(AlgaeArmConstants.kAlgaeArmP, AlgaeArmConstants.kAlgaeArmI, AlgaeArmConstants.kAlgaeArmD);
  private double m_setpoint = AlgaeArmConstants.kMinAngle;
  
  /** Creates a new AlgaeArmSubsystem. */
  public AlgaeArmSubsystem() {
    m_algaeArmMotor.getEncoder().setPosition(0);
    SparkMaxConfig algaeArmConfig = new SparkMaxConfig();
    algaeArmConfig.smartCurrentLimit(40);
    algaeArmConfig.idleMode(IdleMode.kBrake);
    algaeArmConfig.inverted(false);
    m_algaeArmMotor.configure(algaeArmConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Algae Arm Angle", m_algaeArmMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("Algae Arm Setpoint", m_setpoint);
    SmartDashboard.putNumber("Algae Arm PID Error", m_algaeArmPID.getError());
    double output = m_algaeArmPID.calculate(m_algaeArmMotor.getEncoder().getPosition(), m_setpoint);
    SmartDashboard.putNumber("Algae Arm PID Output", output);
    m_algaeArmMotor.set(output);
  }

  public void setDesiredAngle(double angle) {
    m_setpoint = angle;
  }
}
