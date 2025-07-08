// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlgaeRollerConstants;

public class AlgaeRollerSubsytem extends SubsystemBase {
  private SparkMax m_algaeRollerMotor = new SparkMax(AlgaeRollerConstants.kAlgaeRollerPort, MotorType.kBrushless);
  private double m_setpoint = 0.0;
  
  /** Creates a new AlgaeRollerSubsytem. */
  public AlgaeRollerSubsytem() {
    SparkMaxConfig rollerConfig = new SparkMaxConfig();
    rollerConfig.idleMode(IdleMode.kBrake);
    rollerConfig.smartCurrentLimit(40);
    rollerConfig.inverted(false);
    m_algaeRollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }
  
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Algae Roller Speed", m_setpoint);
    m_algaeRollerMotor.set(m_setpoint);
  }

  public void setDesiredSpeed(double speed) {
    m_setpoint = speed;
  }
}
