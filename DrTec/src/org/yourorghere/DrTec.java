package org.yourorghere;

import javax.media.opengl.GL;
import static javax.media.opengl.GL.*;
import javax.media.opengl.glu.GLU;

import javax.media.opengl.glu.GLUquadric;

public class DrTec {

//precision and global variables
    private static final int SLICES = 40;
    private static final int STACKS = 40;
    private GLUquadric q = null;
    private static int mvt = 0;
    //heigth and widht of each components

    private static final float HEIGHT_LEGS = 0.2f;
    private static final float WIDTH_LEGS = 0.185f;
    private static final float HEIGHT_ARMS = 0.22f;
    private static final float WIDTH_ARMS = 0.09f;
    private static final float WIDTH_EYES = 0.17f;
    private static final float WIDTH_HANDS = 0.1f;
    private static final float WIDTH_FINGERS = 0.0525f;
    private static final float WIDTH_SHOES = 0.28f;
    private static final float HEIGHT_SHOES = 0.05f;

    //position of each component int the window
    public DrTec() 
    {
    }

    public void draw_drtec(GL gl, boolean walk, boolean jump) 
    {
        GLU glu = new GLU();
        q = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
        glu.gluQuadricOrientation(q, GLU.GLU_OUTSIDE);
        glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);
        dibujar_ejesXYZ(gl, glu);

        //Stan is walking
        if (walk && mvt % 20 + 10 > 20) 
        {
            dibujar_piernas(gl, glu, 'W', false);
            dibujar_piernas(gl, glu, ' ', true);
            dibujar_brazo_izquiedo(gl, glu, ' ');
            dibujar_brazo_derecho(gl, glu, 'W');
            dibujar_cabeza(gl, glu, false);
        } else if (walk && mvt % 20 + 10 <= 20) 
        {
            dibujar_piernas(gl, glu, ' ', false);
            dibujar_piernas(gl, glu, 'W', true);
            dibujar_brazo_izquiedo(gl, glu, 'W');
            dibujar_brazo_derecho(gl, glu, ' ');
            dibujar_cabeza(gl, glu, false);
        } //stan is jumping
        else if (jump && mvt % 20 + 10 > 20) 
        {
            gl.glTranslatef(0f, 0.35f, 0f);
            dibujar_piernas(gl, glu, 'J', false);
            dibujar_piernas(gl, glu, 'J', true);
            dibujar_brazo_izquiedo(gl, glu, 'J');
            dibujar_brazo_derecho(gl, glu, 'J');
            dibujar_cabeza(gl, glu, true);
        } //stan is normal
        else 
        {
            dibujar_piernas(gl, glu, ' ', false);
            dibujar_piernas(gl, glu, ' ', true);
            dibujar_brazo_izquiedo(gl, glu, ' ');
            dibujar_brazo_derecho(gl, glu, ' ');
            dibujar_cabeza(gl, glu, false);
        }

        mvt++;
        dibujar_cuerpo(gl, glu);
    }

    public void dibujar_ejesXYZ(GL gl, GLU glu) 
    {
        gl.glBegin(GL_LINES);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 2.0f, 0.0f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 2.0f);
        gl.glEnd();
    }

    public void dibujar_cabeza(GL gl, GLU glu, boolean jmp) 
    {
        float x1 = -0.4f, x2 = 0.4f;
        float y1 = -0.4f, y2 = 0.4f;
        set_blue(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.5f, 0.0f);
        gl.glBegin(GL_QUADS);
        //Cara de Frente 
        gl.glVertex3f(x1, y1, 0.3f);
        gl.glVertex3f(x2, y1, 0.3f);
        gl.glVertex3f(x2, y2, 0.3f);
        gl.glVertex3f(x1, y2, 0.3f);
        //Cara de Detras  
        gl.glVertex3f(x2, y1, -0.3f);
        gl.glVertex3f(x1, y1, -0.3f);
        gl.glVertex3f(x1, y2, -0.3f);
        gl.glVertex3f(x2, y2, -0.3f);
        //Cara de Izquierda
        gl.glVertex3f(x1, y1, -0.3f);
        gl.glVertex3f(x1, y1, 0.3f);
        gl.glVertex3f(x1, y2, 0.3f);
        gl.glVertex3f(x1, y2, -0.3f);
        //Cara de Derecha
        gl.glVertex3f(x2, y1, 0.3f);
        gl.glVertex3f(x2, y1, -0.3f);
        gl.glVertex3f(x2, y2, -0.3f);
        gl.glVertex3f(x2, y2, 0.3f);
        //Cara Inferior
        gl.glVertex3f(x1, y1, -0.3f);
        gl.glVertex3f(x1, y1, 0.3f);
        gl.glVertex3f(x2, y1, 0.3f);
        gl.glVertex3f(x2, y1, -0.3f);
        //Cara Superior
        gl.glVertex3f(x1, y2, 0.3f);
        gl.glVertex3f(x2, y2, 0.3f);
        gl.glVertex3f(x2, y2, -0.3f);
        gl.glVertex3f(x1, y2, -0.3f);
        gl.glEnd();
        gl.glPopMatrix();

        dibujar_cara(gl, glu, jmp);
    }

    public void dibujar_cara(GL gl, GLU glu, boolean jmp) 
    {

        //Rostro de boca
        float x1 = -0.35f, x2 = 0.35f;
        float y1 = -0.125f, y2 = 0.1f;
        set_skin_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.25f, 0.03f);
        gl.glBegin(GL_QUADS);
        //Cara de Frente 
        gl.glVertex3f(x1, y1, 0.279f);
        gl.glVertex3f(x2, y1, 0.279f);
        gl.glVertex3f(x2, y2, 0.279f);
        gl.glVertex3f(x1, y2, 0.279f);
        //Cara de Detras  
        gl.glVertex3f(x2, y1, -0.279f);
        gl.glVertex3f(x1, y1, -0.279f);
        gl.glVertex3f(x1, y2, -0.279f);
        gl.glVertex3f(x2, y2, -0.279f);
        //Cara de Izquierda
        gl.glVertex3f(x1, y1, -0.279f);
        gl.glVertex3f(x1, y1, 0.279f);
        gl.glVertex3f(x1, y2, 0.279f);
        gl.glVertex3f(x1, y2, -0.279f);
        //Cara de Derecha
        gl.glVertex3f(x2, y1, 0.279f);
        gl.glVertex3f(x2, y1, -0.279f);
        gl.glVertex3f(x2, y2, -0.279f);
        gl.glVertex3f(x2, y2, 0.279f);
        //Cara Inferior
        gl.glVertex3f(x1, y1, -0.279f);
        gl.glVertex3f(x1, y1, 0.279f);
        gl.glVertex3f(x2, y1, 0.279f);
        gl.glVertex3f(x2, y1, -0.279f);
        //Cara Superior
        gl.glVertex3f(x1, y2, 0.279f);
        gl.glVertex3f(x2, y2, 0.279f);
        gl.glVertex3f(x2, y2, -0.279f);
        gl.glVertex3f(x1, y2, -0.279f);
        gl.glEnd();
        gl.glPopMatrix();


        //Ojos
        
        set_eyes_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-0.18f, 0.59f, 0.2f);
        glu.gluSphere(q, WIDTH_EYES, SLICES, STACKS);
        gl.glTranslatef(0.36f, 0.0f, 0.0f);
        glu.gluSphere(q, WIDTH_EYES, SLICES, STACKS);
        gl.glPopMatrix();
        
        
        
        //Mascara izq
        float m1 = 0f, m2 = -0.5f;
        float n1 = 0.35f, n2 = 1f, n3 = -0.35f;
        set_naranja(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.25f, 0.03f);
        gl.glBegin(GL_TRIANGLES);
        //Cara de Frente 
        gl.glVertex3f(m2, n2, 0.28f);
        gl.glVertex3f(m1, n1, 0.28f);
        gl.glVertex3f(m2, n3, 0.28f);
        
        /*gl.glEnd();
        gl.glPopMatrix();*/
        
        //Mascara der
        float mm1 = 0f, mm2 = 0.5f;
        float nn1 = 0.35f, nn2 = 1f, nn3 = -0.35f;
        set_naranja(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.25f, 0.03f);
        gl.glBegin(GL_TRIANGLES);
        //Cara de Frente 
        gl.glVertex3f(mm2, nn2, 0.28f);
        gl.glVertex3f(mm1, nn1, 0.28f);
        gl.glVertex3f(mm2, nn3, 0.28f);
                      
        //Pupilas
        //set_black_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-0.18f, 0.55f, 0.22f);
        glu.gluSphere(q, 0,0,0);
        gl.glTranslatef(0.36f, 0.0f, 0.00f);
        glu.gluSphere(q, 0,0,0);
        gl.glPopMatrix();

        
        
    }

    public void dibujar_cuerpo(GL gl, GLU glu) 
    {
        float x1 = -0.3f, x2 = 0.3f;
        float y1 = -0.3f, y2 = 0.3f;
        set_naranja(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.1f, 0.0f);
        gl.glBegin(GL_QUADS);
        //Cara de Frente 
        gl.glVertex3f(x1, y1, 0.2f);
        gl.glVertex3f(x2, y1, 0.2f);
        gl.glVertex3f(x2, y2, 0.1f);
        gl.glVertex3f(x1, y2, 0.1f);
        //Cara de Detras  
        gl.glVertex3f(x2, y1, -0.2f);
        gl.glVertex3f(x1, y1, -0.2f);
        gl.glVertex3f(x1, y2, -0.1f);
        gl.glVertex3f(x2, y2, -0.1f);
        //Cara de Izquierda
        gl.glVertex3f(x1, y1, -0.2f);
        gl.glVertex3f(x1, y1, 0.2f);
        gl.glVertex3f(x1, y2, 0.1f);
        gl.glVertex3f(x1, y2, -0.1f);
        //Cara de Derecha
        gl.glVertex3f(x2, y1, 0.2f);
        gl.glVertex3f(x2, y1, -0.2f);
        gl.glVertex3f(x2, y2, -0.1f);
        gl.glVertex3f(x2, y2, 0.1f);
        //Cara Inferior
        gl.glVertex3f(x1, y1, -0.2f);
        gl.glVertex3f(x1, y1, 0.2f);
        gl.glVertex3f(x2, y1, 0.1f);
        gl.glVertex3f(x2, y1, -0.1f);
        //Cara Superior
        gl.glVertex3f(x1, y2, 0.2f);
        gl.glVertex3f(x2, y2, 0.2f);
        gl.glVertex3f(x2, y2, -0.1f);
        gl.glVertex3f(x1, y2, -0.1f);
        gl.glEnd();
        gl.glPopMatrix();

        //estomago
        
        set_eyes_material(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -0.1f, 0.1f);
        glu.gluSphere(q, 0.1, SLICES, STACKS);
        gl.glPopMatrix();
        
    }

    public void dibujar_piernas(GL gl, GLU glu, char c, boolean left) 
    {
        set_blue(gl);
        gl.glPushMatrix();
        //we orientate axes if stan is jumping or is walking
        if (c == 'W') 
        {
            gl.glTranslatef(0f, -0.1f, -0.2f);
            gl.glRotatef(30, -100f, 0f, 0f);
        }
        if (c == 'J') 
        {
            gl.glTranslatef(0f, -0.05f, -0.1f);
            if (left) 
            {
                gl.glRotatef(30, -100f, -100f, 0f);
            } else 
            {
                gl.glRotatef(30, -100f, 100f, 0f);
            }
        }
        //we create legs
        set_blue(gl);
        gl.glPushMatrix();
        if (left) 
        {
            gl.glTranslatef(-0.19f, -0.45f, 0f);
        } else 
        {
            gl.glTranslatef(0.19f, -0.45f, 0f);
        }
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluCylinder(q, WIDTH_LEGS, WIDTH_LEGS, HEIGHT_LEGS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_LEGS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0f, 0f);
        gl.glTranslatef(0f, -HEIGHT_LEGS, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, WIDTH_LEGS, SLICES, STACKS);
        gl.glPopMatrix();

        //we create shoes
        set_black_material(gl);
        gl.glPushMatrix();
        if (left) 
        {
            gl.glTranslatef(-0.34f, -0.7f, -0.15f);
            gl.glScalef(WIDTH_SHOES, HEIGHT_SHOES, 0.25f);
        } else 
        {
            gl.glTranslatef(0.34f, -0.7f, -0.15f);
            gl.glScalef(-WIDTH_SHOES, HEIGHT_SHOES, 0.25f);
        }

        gl.glPopMatrix();
        gl.glPushMatrix();
        if (left)
        {
            gl.glTranslatef(-0.2f, -0.65f, 0.12f);
        } else 
        {
            gl.glTranslatef(0.2f, -0.65f, 0.12f);
        }
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, 0.14, SLICES, STACKS);
        glu.gluCylinder(q, WIDTH_SHOES / 2, WIDTH_SHOES / 2, HEIGHT_SHOES, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0f, 0f);
        gl.glTranslatef(0f, -HEIGHT_SHOES, 0f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        glu.gluDisk(q, 0f, WIDTH_SHOES / 2, SLICES, STACKS);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    public void dibujar_brazo_izquiedo(GL gl, GLU glu, char c) 
    {
        set_skin_material(gl);
        gl.glPushMatrix();
        //we orientate axes if stan is walking or is jumping
        if (c == 'J') 
        {
            gl.glTranslatef(-0.47f, 0.15f, -0.01f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c == 'W') 
        {
            gl.glTranslatef(-0.45f, -0.38f, 0.1f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c == ' ') 
        {
            gl.glTranslatef(-0.45f, -0.42f, 0f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(0.055f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }

        gl.glPopMatrix();
        //we create left arm
        set_blue(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-0.37f, -0.125f, 0f);
        gl.glRotatef(90f, 1f, -0.20f, 0f);
        if (c == 'J') 
        {
            gl.glRotatef(150, 0f, -100f, 0f);
        }
        if (c == 'W')
        {
            gl.glRotatef(20, -1f, 0f, 0f);
        }
        glu.gluCylinder(q, WIDTH_ARMS, WIDTH_ARMS, HEIGHT_ARMS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_ARMS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, 0.20f, 0f);
        gl.glTranslatef(0f, -HEIGHT_ARMS, 0f);
        gl.glRotatef(90f, 1f, -0.20f, 0f);
        if (c != 'J') 
        {
            glu.gluDisk(q, 0f, WIDTH_ARMS, SLICES, STACKS);
        }
        gl.glPopMatrix();
    }

    public void dibujar_brazo_derecho(GL gl, GLU glu, char c) 
    {
        set_skin_material(gl);
        gl.glPushMatrix();
        //we orientate axes if stan is walking or is jumping
        if (c == 'J') 
        {
            gl.glTranslatef(0.47f, 0.15f, -0.01f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c == 'W') 
        {
            gl.glTranslatef(0.45f, -0.38f, 0.1f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.05f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        if (c == ' ') 
        {
            gl.glTranslatef(0.45f, -0.42f, 0f);
            glu.gluSphere(q, WIDTH_HANDS, SLICES, STACKS);
            gl.glTranslatef(-0.055f, 0.015f, 0.05f);
            glu.gluSphere(q, WIDTH_FINGERS, SLICES, STACKS);
        }
        gl.glPopMatrix();
        //we create right arm
        set_blue(gl);
        gl.glPushMatrix();
        gl.glTranslatef(0.37f, -0.125f, 0f);
        gl.glRotatef(90f, 1f, 0.20f, 0f);
        if (c == 'J') 
        {
            gl.glRotatef(150, 0f, 100f, 0f);
        }
        if (c == 'W') 
        {
            gl.glRotatef(20, -1f, 0f, 0f);
        }
        glu.gluCylinder(q, WIDTH_ARMS, WIDTH_ARMS, HEIGHT_ARMS, SLICES, STACKS);
        glu.gluSphere(q, WIDTH_ARMS, SLICES, STACKS);
        gl.glRotatef(90f, -1f, -0.20f, 0f);
        gl.glTranslatef(0f, -HEIGHT_ARMS, 0f);
        gl.glRotatef(90f, 1f, 0.20f, 0f);
        if (c == ' ')
        {
            glu.gluDisk(q, 0f, WIDTH_ARMS, SLICES, STACKS);
        }
        gl.glPopMatrix();
    }

    public void set_blue(GL gl) 
    { // Color
        float[] mat_ambient = {0f, 0f, 1f, 0.0f};
        float[] mat_diffuse = {0f, 0f, 1f, 0.0f};
        float shine = 300f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }

    public void set_naranja(GL gl) 
    { // Color
        float[] mat_ambient = {0.91764f, 0.3686f, 0f, 0.0f};
        float[] mat_diffuse = {0.91764f, 0.3686f, 0f, 0.0f};
        float shine = 300f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }

    public void set_eyes_material(GL gl) 
    {
        float mat_ambient[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float mat_diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
        float mat_specular[] = {0.8f, 0.8f, 0.8f, 1.0f};
        float shine = 51.2f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }
    public void set_escudo(GL gl) 
    {
        float mat_ambient[] = {0.91764f, 0.3686f, 0f, 0.0f};
        float mat_diffuse[] = {0.91764f, 0.3686f, 0f, 0.0f};
        float mat_specular[] = {0.8f, 0.8f, 0.8f, 1.0f};
        float shine = 51.2f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }

    public void set_skin_material(GL gl)
    {
        float[] mat_ambient = {0.9058f, 0.83921f, 0.5294f, 0.0f};
        float[] mat_diffuse = {0.9058f, 0.83921f, 0.5294f, 0.0f};
        float shine = 300f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }
    public void set_white_material(GL gl)
    {
        float[] mat_ambient = {1f, 1f, 1f, 0.0f};
        float[] mat_diffuse = {1f, 1f, 1f, 0.0f};
        float shine = 300f;
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }

    public void set_black_material(GL gl) 
    {
        float mat_ambient[] = {0.0f, 0.0f, 0.0f, 1.0f};
        float mat_diffuse[] = {0.0f, 0.0f, 0.0f, 1.0f};
        float mat_specular[] = {0.0f, 0.0f, 0.0f, 1.0f};
        float shine = 125.2f;

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, mat_ambient, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, mat_diffuse, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, shine);
    }
}