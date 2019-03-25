package com.example.demo

import javax.persistence.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@Entity
@Table(name = "certificate", schema= "nev")
@XmlRootElement(name="HGZXX")
@XmlAccessorType(XmlAccessType.FIELD)
class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "certificate_no")
    @XmlElement(name = "CertificateNO")
    var certificateNo: String = ""

    @Column(name = "data_of_issue")
    @XmlElement(name = "WC0004")
    var dateOfIssue: String = ""

    @Column(name = "vehicle_manufacturer")
    @XmlElement(name = "WC0005")
    var vehicleManufacturer: String = ""
    @Column(name = "vehicle_brand")
    @XmlElement(name = "WC0009")
    var vehicleBrand: String = ""

    @Column(name = "vehicle_name")
    @XmlElement(name = "WC0008")
    var vehicleName: String = ""

    @Column(name = "vehicle_model")
    @XmlElement(name = "WC0010")
    var vehicleModel: String = ""

    @Column(name = "vehicle_identification_no")
    @XmlElement(name = "WC0014")
    var vehicleIdentificationNo: String = ""

    @Column(name = "engine_model")
    @XmlElement(name = "WC0017")
    var engineModel: String = ""

    @Column(name = "engine_number")
    @XmlElement(name = "WC0016")
    var engineNumber: String = ""

    @Column(name = "fuel_type")
    @XmlElement(name = "WC0018")
    var fuelType: String = ""

    @Column(name = "total_mass")
    @XmlElement(name = "WC0037")
    var totalMass: String = ""

    @Column(name = "curb_quality")
    @XmlElement(name = "WC0039")
    var curbQuality: String = ""

    @Column(name = "passenger_no_allowed")
    @XmlElement(name = "WC0042")
    var passengerNoAllowed: String = ""

    @Column(name = "highest_design_speed")
    @XmlElement(name = "WC0049")
    var highestDesignDpeed: String = ""

    @Column(name = "vehicle_manufacture_date")
    @XmlElement(name = "WC0050")
    var vehicleManufactureDate: String = ""

    @Column(name = "remark")
    @XmlElement(name = "WC0051")
    var remark: String = ""

    override fun toString(): String {
        return "Certificate(id=$id, certificateNo='$certificateNo', dateOfIssue='$dateOfIssue', vehicleManufacturer='$vehicleManufacturer', vehicleBrand='$vehicleBrand', vehicleName='$vehicleName', vehicleModel='$vehicleModel', vehicleIdentificationNo='$vehicleIdentificationNo', engineModel='$engineModel', engineNumber='$engineNumber', fuelType='$fuelType', totalMass='$totalMass', curbQuality='$curbQuality', passengerNoAllowed='$passengerNoAllowed', highestDesignDpeed='$highestDesignDpeed', vehicleManufactureDate='$vehicleManufactureDate', remark='$remark')"
    }


}
