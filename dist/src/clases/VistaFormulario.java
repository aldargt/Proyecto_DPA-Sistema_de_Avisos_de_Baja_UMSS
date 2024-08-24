/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Hp
 */
public class VistaFormulario extends javax.swing.JFrame {

    JButton btnVer = new JButton("VER");
    String rutaClick = "";
    String otrosClick = "";

    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public VistaFormulario() {
        initComponents();
        btnVer.setName("ver");

        obtenerAnio();
        obternerFechaActualCF();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Sistema de Registro de Avisos de Baja del Asegurado");

        labelTitulo.setFont(new Font("Verdana", 3, 30));
        labelTitulo.setForeground(new Color(243, 255, 255));
        String anioTitulo = cbxAnio.getSelectedItem().toString();
        labelTitulo1.setText(anioTitulo);
        labelTitulo1.setFont(new Font("Verdana", 3, 30));
        labelTitulo1.setForeground(new Color(243, 255, 255));

        jtDocumentaciones.setDefaultRenderer(Object.class, new Render());
        jtDocumentaciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jtDocumentaciones.getTableHeader().setFont(new Font("Verdana", Font.ITALIC, 11));
        jtDocumentaciones.getTableHeader().setOpaque(false);
        jtDocumentaciones.getTableHeader().setBackground(new Color(95, 0, 62));
        jtDocumentaciones.getTableHeader().setForeground(new Color(255, 255, 255));
        jtDocumentaciones.setRowHeight(25);

        try {
            jtDocumentaciones.setModel(modelo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion();

            String anioConsulta = cbxAnio.getSelectedItem().toString();
            //System.out.println(anioConsulta);

            String sql = "SELECT * FROM avisos WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("    FECHA DE EMISIÓN");
            modelo.addColumn("                      NOMBRE COMPLETO");
            modelo.addColumn("            MATRICULA");
            modelo.addColumn("              U. S. C.");
            modelo.addColumn("       FECHA DE BAJA");
            modelo.addColumn("                       MOTIVO DE BAJA");
            modelo.addColumn("           ARCHIVO");

            int[] anchos = {0, 50, 200, 60, 60, 50, 190, 40};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1) + " " + rs.getObject(i + 2) + " " + rs.getObject(i + 3);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 5:
                            String fechaBajaRecibida = darFormato(rs.getDate(i + 3));
                            filas[i] = "  " + fechaBajaRecibida;
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 7:
                            filas[i] = btnVer;
                            break;
                    }
                }
                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            //System.err.println(ex.toString());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        labelTitulo1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxAnio = new javax.swing.JComboBox();
        cajaTextoBuscar = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        labelFechaEst = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDocumentaciones = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cajaTextoPaterno = new javax.swing.JTextField();
        cajaTextoNombres = new javax.swing.JTextField();
        cajaTextoMatricula = new javax.swing.JTextField();
        cajaTextoMaterno = new javax.swing.JTextField();
        cajaTextoUltimos = new javax.swing.JTextField();
        cajaTextoDiab = new javax.swing.JTextField();
        cajaTextoMesb = new javax.swing.JTextField();
        cajaTextoAniob = new javax.swing.JTextField();
        cajaTextoOtros = new javax.swing.JTextField();
        cbxMotivo = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        botonGuardar = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        botonLimpiar = new javax.swing.JButton();
        botonGenerar = new javax.swing.JButton();
        labelFechaActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 0, 102));

        labelTitulo.setText("REGISTRO DE AVISOS DE BAJA DE LOS ASEGURADOS GESTIÓN -");

        labelTitulo1.setText("ANIO");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑO:");

        cbxAnio.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cbxAnio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAnioActionPerformed(evt);
            }
        });

        cajaTextoBuscar.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoBuscarActionPerformed(evt);
            }
        });

        botonBuscar.setBackground(new java.awt.Color(255, 255, 255));
        botonBuscar.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
        botonBuscar.setForeground(new java.awt.Color(0, 51, 102));
        botonBuscar.setText("BUSCAR NOMBRE");
        botonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        labelFechaEst.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        labelFechaEst.setForeground(new java.awt.Color(255, 255, 255));
        labelFechaEst.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelFechaEst.setText("FECHA ACTUAL:");

        jtDocumentaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtDocumentaciones.setSelectionBackground(new java.awt.Color(205, 190, 231));
        jtDocumentaciones.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jtDocumentaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtDocumentaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtDocumentacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtDocumentaciones);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("AP. PATERNO:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("AP. MATERNO:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("NOMBRE(S):");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("MATRÍCULA:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ULTIMO SALARIO COTIZADO:");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("FECHA DE BAJA:");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("MOTIVO DE BAJA:");

        cajaTextoPaterno.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoPaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoPaternoActionPerformed(evt);
            }
        });

        cajaTextoNombres.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoNombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoNombresActionPerformed(evt);
            }
        });

        cajaTextoMatricula.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoMatriculaActionPerformed(evt);
            }
        });

        cajaTextoMaterno.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoMaterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoMaternoActionPerformed(evt);
            }
        });

        cajaTextoUltimos.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoUltimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoUltimosActionPerformed(evt);
            }
        });

        cajaTextoDiab.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoDiab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoDiabActionPerformed(evt);
            }
        });

        cajaTextoMesb.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoMesb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoMesbActionPerformed(evt);
            }
        });

        cajaTextoAniob.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoAniob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoAniobActionPerformed(evt);
            }
        });

        cajaTextoOtros.setEditable(false);
        cajaTextoOtros.setBackground(new java.awt.Color(51, 0, 102));
        cajaTextoOtros.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoOtrosActionPerformed(evt);
            }
        });

        cbxMotivo.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cbxMotivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FIN NOMBRAMIENTO", "FALLECIMIENTO", "JUBILACIÓN", "REGULARIZACIÓN", "RENUNCIA VOLUNTARIA", "OTROS" }));
        cbxMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMotivoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Todos los registros guardados generarán su propio archivo digital, el cual contendrá el ");

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("respectivo Nº Patronal: 02-921001");

        botonGuardar.setBackground(new java.awt.Color(255, 255, 255));
        botonGuardar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonGuardar.setForeground(new java.awt.Color(0, 51, 102));
        botonGuardar.setText("GUARDAR");
        botonGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonModificar.setBackground(new java.awt.Color(255, 255, 255));
        botonModificar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonModificar.setForeground(new java.awt.Color(0, 51, 102));
        botonModificar.setText("MODIFICAR");
        botonModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        botonEliminar.setBackground(new java.awt.Color(255, 255, 255));
        botonEliminar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonEliminar.setForeground(new java.awt.Color(0, 51, 102));
        botonEliminar.setText("ELIMINAR");
        botonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        botonLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        botonLimpiar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonLimpiar.setForeground(new java.awt.Color(0, 51, 102));
        botonLimpiar.setText("LIMPIAR");
        botonLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarActionPerformed(evt);
            }
        });

        botonGenerar.setBackground(new java.awt.Color(255, 255, 255));
        botonGenerar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonGenerar.setForeground(new java.awt.Color(0, 51, 102));
        botonGenerar.setText("GENERAR REPORTE ANUAL");
        botonGenerar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });

        labelFechaActual.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labelFechaActual.setForeground(new java.awt.Color(255, 255, 255));
        labelFechaActual.setText("MATRÍCULA:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(labelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTitulo1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(cbxAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(cajaTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelFechaEst, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelFechaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cajaTextoDiab, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cajaTextoMesb, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cajaTextoAniob, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(botonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6)
                                        .addGap(201, 201, 201))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(cajaTextoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel10))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cbxMotivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(12, 12, 12)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cajaTextoOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cajaTextoNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(35, 35, 35))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 670, Short.MAX_VALUE)))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(cajaTextoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(71, 71, 71)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cajaTextoUltimos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitulo)
                    .addComponent(labelTitulo1))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar)
                    .addComponent(labelFechaEst)
                    .addComponent(labelFechaActual))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cajaTextoUltimos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cajaTextoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cajaTextoPaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaTextoMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaTextoNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaTextoDiab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoMesb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoAniob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaTextoOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGuardar)
                    .addComponent(botonModificar)
                    .addComponent(botonEliminar)
                    .addComponent(botonLimpiar)
                    .addComponent(botonGenerar))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void cbxAnioActionPerformed(java.awt.event.ActionEvent evt) {
        reiniciar();
        limpiar();
        String anioTitulo = cbxAnio.getSelectedItem().toString();
        labelTitulo1.setText(anioTitulo);
    }

    private void cajaTextoBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {

        String campo = cajaTextoBuscar.getText();
        String anioConsulta = cbxAnio.getSelectedItem().toString();
        String sql = "";
        String where = "";
        if (!"".equals(campo)) {
            where = "WHERE nombres LIKE '%" + campo + "%' OR apPaterno LIKE '%" + campo + "%' OR apMaterno LIKE '%" + campo +"%'";
            sql = "SELECT * FROM avisos " + where + " AND YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
        } else {
            sql = "SELECT * FROM avisos WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jtDocumentaciones.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConexion();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("    FECHA DE EMISIÓN");
            modelo.addColumn("                      NOMBRE COMPLETO");
            modelo.addColumn("            MATRICULA");
            modelo.addColumn("              U. S. C.");
            modelo.addColumn("       FECHA DE BAJA");
            modelo.addColumn("                       MOTIVO DE BAJA");
            modelo.addColumn("           ARCHIVO");

            int[] anchos = {0, 50, 200, 60, 60, 50, 190, 40};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1) + " " + rs.getObject(i + 2) + " " + rs.getObject(i + 3);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 5:
                            String fechaBajaRecibida = darFormato(rs.getDate(i + 3));
                            filas[i] = "  " + fechaBajaRecibida;
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 7:
                            filas[i] = btnVer;
                            break;
                    }
                }
                modelo.addRow(filas);
            }

        } catch (Exception ex) {
            //System.err.println(ex.toString());
        }
    }

    private void cajaTextoDiaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoMesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoAnioActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jtDocumentacionesMouseClicked(java.awt.event.MouseEvent evt) {

        int column = jtDocumentaciones.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jtDocumentaciones.getRowHeight();
        String rutaCadena = "";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Conexion objCon = new Conexion();
            java.sql.Connection conn = objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String id = jtDocumentaciones.getValueAt(Fila, 0).toString();

            ps = conn.prepareStatement("SELECT * FROM avisos WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                //cajaTextoFechaActual.setText(darFormato(rs.getDate("fecha_actual")));
                //cajaTextoDia.setText(recibirDia(rs.getDate("fecha")));
                //cajaTextoMes.setText(recibirMes(rs.getDate("fecha")));
                //cajaTextoAnio.setText(recibirAnio(rs.getDate("fecha")));
                cajaTextoPaterno.setText(rs.getString("apPaterno"));
                cajaTextoMaterno.setText(rs.getString("apMaterno"));
                cajaTextoNombres.setText(rs.getString("nombres"));
                cajaTextoMatricula.setText(rs.getString("matricula"));
                cajaTextoUltimos.setText(rs.getString("ultSalar"));
                cajaTextoDiab.setText(recibirDia(rs.getDate("fechaBaja")));
                cajaTextoMesb.setText(recibirMes(rs.getDate("fechaBaja")));
                cajaTextoAniob.setText(recibirAnio(rs.getDate("fechaBaja")));
                if(rs.getString("motBaja").equals("FIN NOMBRAMIENTO") || rs.getString("motBaja").equals("FALLECIMIENTO") || rs.getString("motBaja").equals("JUBILACIÓN") || rs.getString("motBaja").equals("REGULARIZACIÓN") || rs.getString("motBaja").equals("RENUNCIA VOLUNTARIA")){
                    cbxMotivo.setSelectedItem(rs.getString("motBaja"));
                    cajaTextoOtros.setText("");
                    otrosClick = "";
                }else{
                    cbxMotivo.setSelectedItem("OTROS");
                    cajaTextoOtros.setText(rs.getString("motBaja"));
                    otrosClick = rs.getString("motBaja");
                }
                rutaCadena = rs.getString("ruta");
                rutaClick = rs.getString("ruta");
            }

            if (row < jtDocumentaciones.getRowCount() && row >= 0 && column < jtDocumentaciones.getColumnCount() && column >= 0) {
                Object value = jtDocumentaciones.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                    JButton boton = (JButton) value;

                    if (boton.getName().equals("ver")) {
                        try {
                            abrirarchivo(rutaCadena);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo: " + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + id + ".pdf", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            //System.out.println(ex.toString());
        }

    }

    private void cajaTextoPaternoActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoNombresActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoMatriculaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoMaternoActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoUltimosActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoDiabActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoMesbActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoAniobActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoOtrosActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void cbxMotivoActionPerformed(java.awt.event.ActionEvent evt) {
        if((cbxMotivo.getSelectedItem().toString()).equals("OTROS")){
            cajaTextoOtros.setEditable(true);
            cajaTextoOtros.setBackground(Color.white);
            cajaTextoOtros.setForeground(Color.black);
            cajaTextoOtros.setText(otrosClick);
        }else{
            cajaTextoOtros.setEditable(false);
            cajaTextoOtros.setBackground(new Color(51, 0, 102));
            cajaTextoOtros.setForeground(Color.gray);
            cajaTextoOtros.setText("");
        }
    }

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int indice = 0;
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Conexion objCon = new Conexion();
            Connection conexion = (Connection) objCon.getConexion();

            ResultSet rs = null;
            ps2 = conexion.prepareStatement("SELECT AUTO_INCREMENT FROM `information_schema`.`tables` WHERE TABLE_SCHEMA = 'registros_avisos' AND TABLE_NAME = 'avisos'");
            rs = ps2.executeQuery();
            while (rs.next()) {
                indice = rs.getInt(1);
            }

            ps = conexion.prepareStatement("INSERT INTO avisos (fecha, apPaterno, apMaterno, nombres, matricula, ultSalar, fechaBaja, motBaja, ruta) VALUES (?,?,?,?,?,?,?,?,?)");
            //ps.setString(1, cajaTextoAnio.getText() + "-" + cajaTextoMes.getText() + "-" + cajaTextoDia.getText());
            ps.setString(1, obtenerFechaActualSF());
            ps.setString(2, cajaTextoPaterno.getText());
            ps.setString(3, cajaTextoMaterno.getText());
            ps.setString(4, cajaTextoNombres.getText());
            ps.setString(5, cajaTextoMatricula.getText());
            ps.setString(6, cajaTextoUltimos.getText());
            ps.setString(7, cajaTextoAniob.getText() + "-" + cajaTextoMesb.getText() + "-" + cajaTextoDiab.getText());
            if((cbxMotivo.getSelectedItem().toString()).equals("OTROS")){
                ps.setString(8, cajaTextoOtros.getText());
            }else{
                ps.setString(8, cbxMotivo.getSelectedItem().toString());
            }
            ps.setString(9, "C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + indice + ".pdf");
            ps.execute();

            try {
                Conexion objCon2 = new Conexion();
                Connection conexion2 = (Connection) objCon2.getConexion();

                Map parametro = new HashMap();

                parametro.put("llave", indice);
                parametro.put("diap", cajaTextoDiab.getText());
                parametro.put("mesp", darFormatoMes(cajaTextoMesb.getText()));
                parametro.put("aniop", cajaTextoAniob.getText());

                String path = "src\\reportes\\Aviso.jasper";
                //se carga el reporte
                //URL in = this.getClass().getResource("../reportes/Solvencia.jasper");
                //jasperReport = (JasperReport) JRLoader.loadObject(in);
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);

                //se procesa el archivo jasper
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conexion2);
                //se crea el archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + indice + ".pdf");


            } catch (JRException ex) {
                System.err.println("Error iReport: " + ex.getMessage());
            }

            reiniciar();

            int opcion = JOptionPane.showConfirmDialog(this, "El Aviso de Baja del Asegurado ha sido Registrado. ¿Desea vizualizar el documento generado?", "Realizado", JOptionPane.YES_NO_OPTION);
            switch (opcion) {
                case 0:
                    abrirarchivo("C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + indice + ".pdf");
                    break;
                case 1:
                    System.out.println("boton nooo");
                    break;
            }

            //JOptionPane.showMessageDialog(null, "Certificado de Solvencia Registrado", "Realizado", JOptionPane.INFORMATION_MESSAGE);

            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar el Aviso de Baja del Asegurado", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex);
        }
    }

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {
        PreparedStatement ps = null;

        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Conexion objCon = new Conexion();
            Connection conn = (Connection) objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String codigo = jtDocumentaciones.getValueAt(Fila, 0).toString();

            ps = conn.prepareStatement("UPDATE avisos SET fecha=?, apPaterno=?, apMaterno=?, nombres=?, matricula=?, ultSalar=?, fechaBaja=?, motBaja=?, ruta=? WHERE id=?");

            ps.setString(1, obtenerFechaActualSF());
            ps.setString(2, cajaTextoPaterno.getText());
            ps.setString(3, cajaTextoMaterno.getText());
            ps.setString(4, cajaTextoNombres.getText());
            ps.setString(5, cajaTextoMatricula.getText());
            ps.setString(6, cajaTextoUltimos.getText());
            ps.setString(7, cajaTextoAniob.getText() + "-" + cajaTextoMesb.getText() + "-" + cajaTextoDiab.getText());
            if((cbxMotivo.getSelectedItem().toString()).equals("OTROS")){
                ps.setString(8, cajaTextoOtros.getText());
            }else{
                ps.setString(8, cbxMotivo.getSelectedItem().toString());
            }
            ps.setString(9, "C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + codigo + ".pdf");
            ps.setString(10, codigo);
            ps.execute();

            File fichero = new File(rutaClick);
            if (fichero.delete()) {
                System.out.println("El fichero " + rutaClick + " ha sido borrado satisfactoriamente");
            } else {
                System.out.println("El fichero no puede ser borrado");
            }

            try {
                Conexion objCon2 = new Conexion();
                Connection conexion2 = (Connection) objCon2.getConexion();

                Map parametro = new HashMap();

                parametro.put("llave", codigo);
                parametro.put("diap", cajaTextoDiab.getText());
                parametro.put("mesp", darFormatoMes(cajaTextoMesb.getText()));
                parametro.put("aniop", cajaTextoAniob.getText());

                String path = "src\\reportes\\Aviso.jasper";
                //se carga el reporte
                //URL in = this.getClass().getResource("../reportes/Solvencia.jasper");
                //jasperReport = (JasperReport) JRLoader.loadObject(in);
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);

                //se procesa el archivo jasper
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conexion2);
                //se crea el archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + codigo + ".pdf");


            } catch (JRException ex) {
                System.err.println("Error iReport: " + ex.getMessage());
            }

            reiniciar();

            int opcion = JOptionPane.showConfirmDialog(this, "El Aviso de Baja del Asegurado ha sido Modificado. ¿Desea vizualizar el documento generado?", "Realizado", JOptionPane.YES_NO_OPTION);
            switch (opcion) {
                case 0:
                    abrirarchivo("C:/Users/" + System.getProperty("user.name") + "/Desktop/AVISOS DE BAJA UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoPaterno.getText() + " " + cajaTextoMaterno.getText() + " " + cajaTextoNombres.getText() + " - ID " + codigo + ".pdf");
                    break;
                case 1:
                    System.out.println("boton nooo");
                    break;
            }

            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Modificar el Aviso de Baja del Asegurado", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha modificado ningún registro, ya que no se escogió alguno", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {

        PreparedStatement ps = null;
        try {

            Conexion objCon = new Conexion();
            Connection conn = (Connection) objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String codigo = jtDocumentaciones.getValueAt(Fila, 0).toString();

            ps = conn.prepareStatement("DELETE FROM avisos WHERE id=?");
            ps.setString(1, codigo);
            ps.execute();

            File fichero = new File(rutaClick);
            if (fichero.delete()) {
                System.out.println("El fichero " + rutaClick + " ha sido borrado satisfactoriamente");
            } else {
                System.out.println("El fichero no puede ser borrado");
            }

            reiniciar();
            JOptionPane.showMessageDialog(null, "El Aviso de Baja del Asegurado ha sido Eliminado", "Realizado", JOptionPane.INFORMATION_MESSAGE);
            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar el Aviso de Baja del Asegurado", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha eliminado ningún registro, ya que no se escogió alguno", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void botonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiar();
        reiniciar();
    }

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
         try {
            Conexion con = new Conexion();
            Connection conn = (Connection) con.getConexion();

            Conexion objCon =    new Conexion();
            Connection conexion = (Connection) objCon.getConexion();
            PreparedStatement ps2 = null;
            int cantidad = 0;
            ResultSet rs = null;
            ps2 = conexion.prepareStatement("SELECT COUNT(*) FROM registros_avisos.avisos WHERE YEAR(fecha)="+cbxAnio.getSelectedItem().toString());
            rs = ps2.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }

            JasperReport reporte = null;
            String path = "src\\reportes\\RepAnualAvis.jasper";

            Map parametro = new HashMap();
            parametro.put("gestion", cbxAnio.getSelectedItem().toString());
            parametro.put("cantidad", cantidad);
            parametro.put("anio", cbxAnio.getSelectedItem().toString());

            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conn);

            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

            view.setExtendedState(view.MAXIMIZED_BOTH);

        } catch (JRException ex) {
            Logger.getLogger(VistaFormulario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Generar", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex);
        }
    }
    private void reiniciar() {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jtDocumentaciones.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion();

            String anioConsulta = cbxAnio.getSelectedItem().toString();

            String sql = "SELECT * FROM avisos WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("    FECHA DE EMISIÓN");
            modelo.addColumn("                      NOMBRE COMPLETO");
            modelo.addColumn("            MATRICULA");
            modelo.addColumn("              U. S. C.");
            modelo.addColumn("       FECHA DE BAJA");
            modelo.addColumn("                       MOTIVO DE BAJA");
            modelo.addColumn("           ARCHIVO");

            int[] anchos = {0, 50, 200, 60, 60, 50, 190, 40};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1) + " " + rs.getObject(i + 2) + " " + rs.getObject(i + 3);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 5:
                            String fechaBajaRecibida = darFormato(rs.getDate(i + 3));
                            filas[i] = "  " + fechaBajaRecibida;
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 3);
                            break;
                        case 7:
                            filas[i] = btnVer;
                            break;
                    }
                }
                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            //System.err.println(ex.toString());
        }
    }

    private void limpiar() {
        //obternerFechaActualCF();
        cajaTextoBuscar.setText("");
        cajaTextoPaterno.setText("");
        cajaTextoMaterno.setText("");
        cajaTextoNombres.setText("");
        cajaTextoMatricula.setText("");
        cajaTextoUltimos.setText("");
        cajaTextoDiab.setText("");
        cajaTextoMesb.setText("");
        cajaTextoAniob.setText("");
        cbxMotivo.setSelectedIndex(0);
        cajaTextoOtros.setText("");
        otrosClick = "";
    }

    public String darFormato(Date fecha) {
        String mesString = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int diaActual = cal.get(Calendar.DAY_OF_MONTH);
        int mesActual = cal.get(Calendar.MONTH);
        int año = cal.get(Calendar.YEAR);
        switch (mesActual) {
            case 0:
                mesString = "Ene.";
                break;
            case 1:
                mesString = "Feb.";
                break;
            case 2:
                mesString = "Mar.";
                break;
            case 3:
                mesString = "Abr.";
                break;
            case 4:
                mesString = "May.";
                break;
            case 5:
                mesString = "Jun.";
                break;
            case 6:
                mesString = "Jul.";
                break;
            case 7:
                mesString = "Ago.";
                break;
            case 8:
                mesString = "Sep.";
                break;
            case 9:
                mesString = "Oct.";
                break;
            case 10:
                mesString = "Nov.";
                break;
            case 11:
                mesString = "Dic.";
                break;
        }
        //System.out.println("este es el mes: "+mesString+" "+diaActual);
        return diaActual + " " + mesString + " " + año;
    }
    public String darFormatoMes(String mes) {
        int evMes = Integer.parseInt(mes);
        String mesString = "";
        switch (evMes) {
            case 1:
                mesString = "ENERO";
                break;
            case 2:
                mesString = "FEBRERO";
                break;
            case 3:
                mesString = "MARZO";
                break;
            case 4:
                mesString = "ABRIL";
                break;
            case 5:
                mesString = "MAYO";
                break;
            case 6:
                mesString = "JUNIO";
                break;
            case 7:
                mesString = "JULIO";
                break;
            case 8:
                mesString = "AGOSTO";
                break;
            case 9:
                mesString = "SEPTIEMBRE";
                break;
            case 10:
                mesString = "OCTUBRE";
                break;
            case 11:
                mesString = "NOVIEMBRE";
                break;
            case 12:
                mesString = "DICIEMBRE";
                break;
        }
        return mesString;
    }

    public void obtenerAnio() {
        String anioString = "";
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        String[] opciones = new String[10];
        for (int i = 0; i < 10; i++) {
            anioString = Integer.toString(año);
            opciones[i] = anioString;
            año = año - 1;
        }
        cbxAnio.setModel(new javax.swing.DefaultComboBoxModel(opciones));
    }

    public String obtenerFechaActualSF(){
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH)+1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        return año+"-"+mes+"-"+dia;
    }

    public void obternerFechaActualCF() {
        String mesString = "";
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        switch (mes) {
            case 0:
                mesString = "Enero";
                break;
            case 1:
                mesString = "Febrero";
                break;
            case 2:
                mesString = "Marzo";
                break;
            case 3:
                mesString = "Abril";
                break;
            case 4:
                mesString = "Mayo";
                break;
            case 5:
                mesString = "Junio";
                break;
            case 6:
                mesString = "Julio";
                break;
            case 7:
                mesString = "Agosto";
                break;
            case 8:
                mesString = "Septiembre";
                break;
            case 9:
                mesString = "Octubre";
                break;
            case 10:
                mesString = "Noviembre";
                break;
            case 11:
                mesString = "Diciembre";
                break;
        }
        labelFechaActual.setText(dia + " de " + mesString + " del " + año);
        //System.out.println(mes);
        //System.out.println("Fecha Actual: " + dia + "/" + mesString + "/" + año);
    }

    public String recibirDia(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(dia);
    }

    public String recibirMes(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int mes = cal.get(Calendar.MONTH);
        //System.out.println(mes);
        return Integer.toString(mes + 1);
    }

    public String recibirAnio(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int anio = cal.get(Calendar.YEAR);
        return Integer.toString(anio);
    }

    public void abrirarchivo(String archivo) {

        try {

            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaFormulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonLimpiar;
    private javax.swing.JButton botonModificar;
    //private javax.swing.JTextField cajaTextoAnio;
    private javax.swing.JTextField cajaTextoAniob;
    private javax.swing.JTextField cajaTextoBuscar;
    //private javax.swing.JTextField cajaTextoDia;
    private javax.swing.JTextField cajaTextoDiab;
    private javax.swing.JTextField cajaTextoMaterno;
    private javax.swing.JTextField cajaTextoMatricula;
    //private javax.swing.JTextField cajaTextoMes;
    private javax.swing.JTextField cajaTextoMesb;
    private javax.swing.JTextField cajaTextoNombres;
    private javax.swing.JTextField cajaTextoOtros;
    private javax.swing.JTextField cajaTextoPaterno;
    private javax.swing.JTextField cajaTextoUltimos;
    private javax.swing.JComboBox cbxAnio;
    private javax.swing.JComboBox cbxMotivo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtDocumentaciones;
    private javax.swing.JLabel labelFechaEst;
    private javax.swing.JLabel labelFechaActual;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelTitulo1;
    // End of variables declaration
}
