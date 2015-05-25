package com.phei.netty.protocol.http.xml.pojo;

/**
 * Supported shipment methods. The "INTERNATIONAL" shipment methods can only be
 * used for orders with shipping addresses outside the U.S., and one of these
 * methods is required in this case.
 */
public enum Shipping {
    STANDARD_MAIL, PRIORITY_MAIL, INTERNATIONAL_MAIL, DOMESTIC_EXPRESS, INTERNATIONAL_EXPRESS
}