package org.apache.maven.tools.plugin.annotations.scanner.visitors;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.tools.plugin.annotations.scanner.MojoAnnotationsScanner;
import org.codehaus.plexus.logging.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

/**
 * @author Olivier Lamy
 */
public class MojoFieldVisitor
    implements FieldVisitor
{
    private Logger logger;

    private String fieldName;

    private MojoAnnotationVisitor mojoAnnotationVisitor;

    MojoFieldVisitor( Logger logger, String fieldName )
    {
        this.logger = logger;
        this.fieldName = fieldName;
    }

    public MojoAnnotationVisitor getMojoAnnotationVisitor()
    {
        return mojoAnnotationVisitor;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public AnnotationVisitor visitAnnotation( String desc, boolean visible )
    {
        logger.debug( "MojoFieldVisitor#visitAnnotation:" + desc );
        String annotationClassName = Type.getType( desc ).getClassName();
        if ( !MojoAnnotationsScanner.acceptedFieldLevelAnnotationClasses.contains( annotationClassName ) )
        {
            return null;
        }
        mojoAnnotationVisitor = new MojoAnnotationVisitor( logger, annotationClassName );
        return mojoAnnotationVisitor;
    }

    public void visitAttribute( Attribute attribute )
    {
        logger.debug( "MojoFieldVisitor#visitAttribute" );
    }

    public void visitEnd()
    {
        logger.debug( "MojoFieldVisitor#visitEnd" );
    }
}