using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace TollBooth
{
    public partial class FLogin : Form
    {
        public FLogin()
        {
            InitializeComponent();
        }

        private void FLogin_Load(object sender, EventArgs e)
        {
            GeneralFunctions.tollid = "";
            string error = "", qry = "";
            qry = "SELECT TollId, TollName FROM mtolls";
            DataTable dt = GeneralFunctions.ExecuteDataTable(qry, out error);
            cmbtoll.DataSource = dt;
            cmbtoll.DisplayMember = "TollName";
            cmbtoll.ValueMember = "TollId";
        }

        private void btnlogin_Click(object sender, EventArgs e)
        {
            string error = "", qry = "";
            qry = @"select * from tolluser where tollid="+cmbtoll.SelectedValue+" and username='" + txtuser.Text +
                    "' and userpassword='" + txtpass.Text + "'";
            DataTable dtlog = GeneralFunctions.ExecuteDataTable(qry, out error);
            if (dtlog.Rows.Count > 0)
            {
                GeneralFunctions.tollid = cmbtoll.SelectedValue.ToString();
                GeneralFunctions.tollname = cmbtoll.Text.ToString();
                Form1 f1 = new Form1();
                f1.Show();
                this.Hide();
            }
            else
            {
                GeneralFunctions.tollid = "";
                MessageBox.Show("Invalid User Name or Password");
            }
        }

        private void btnexit_Click(object sender, EventArgs e)
        {
            this.Close();
        }

		private void cmbtoll_SelectedIndexChanged(object sender, EventArgs e)
		{

		}
	}
}
