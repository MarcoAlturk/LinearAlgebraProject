package com.example.linearalgebrasolver.PolyMessh;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.List;

public class Joint extends Node {
    // Custom property for the joint's local-to-parent transform
    private final ObjectProperty<Transform> jointLocalToParentTransform = new SimpleObjectProperty<>(new Affine());
    private Joint parentJoint;
    private final List<Joint> childJoints = new ArrayList<>();

    /**
     * Creates a new Joint with an initial transform.
     *
     * @param initialTransform The initial local-to-parent transform for the joint.
     */
    public Joint(Transform initialTransform) {
        setJointLocalToParentTransform(initialTransform);
    }

    /**
     * Gets the local-to-parent transform of the joint.
     *
     * @return The local-to-parent transform.
     */
    public Transform getJointLocalToParentTransform() {
        return jointLocalToParentTransform.get();
    }

    /**
     * Sets the local-to-parent transform of the joint.
     *
     * @param transform The new local-to-parent transform.
     */
    public void setJointLocalToParentTransform(Transform transform) {
        jointLocalToParentTransform.set(transform);
    }

    /**
     * The property for the local-to-parent transform.
     *
     * @return The local-to-parent transform property.
     */
    public ObjectProperty<Transform> jointLocalToParentTransformProperty() {
        return jointLocalToParentTransform;
    }

    /**
     * Gets the parent joint.
     *
     * @return The parent joint, or null if this is a root joint.
     */
    public Joint getParentJoint() {
        return parentJoint;
    }

    /**
     * Sets the parent joint for this joint.
     *
     * @param parent The parent joint.
     */
    public void setParentJoint(Joint parent) {
        if (this.parentJoint != null) {
            this.parentJoint.childJoints.remove(this);
        }
        this.parentJoint = parent;
        if (parent != null) {
            parent.childJoints.add(this);
        }
    }

    /**
     * Gets the child joints of this joint.
     *
     * @return A list of child joints.
     */
    public List<Joint> getChildJoints() {
        return childJoints;
    }

}
