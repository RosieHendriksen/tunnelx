////////////////////////////////////////////////////////////////////////////////
// TunnelX -- Cave Drawing Program  
// Copyright (C) 2002  Julian Todd.  
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.  
////////////////////////////////////////////////////////////////////////////////
package Tunnel;

import java.awt.Graphics; 

//
//
// OneStation
//
//
class OneStation
{
	// unique identifier
	public String name;
	OneTunnel utunnel;

	// location and flag used to set the location
	Vec3 Loc = null;
	Vec3 tLoc = new Vec3();

	// used to give the index for vrml, and for cross sections in whole survey mode.
	int vsig;

	// transformed for viewing points
	public int TLocX = 0;
	public int TLocY = 0;
	public int TLocZ = 0;

	// connections to other legs
	OneLeg olconn[] = null;
	int njl = 0;

	// position set for calculating location
	boolean bPositionSet = false;

	/////////////////////////////////////////////
	public OneStation(OneTunnel lutunnel, String lname)
	{
		name = lname;
		utunnel = lutunnel;
		vsig = -1;
	}

	/////////////////////////////////////////////
	float AngDiff(float ang)
	{
		if (ang < 0.0F)
			ang += 360.0F;
		if (ang > 360.0F)
			ang -= 360.0F; 
		return Math.min(ang, 360.0F - ang); 
	}


	/////////////////////////////////////////////
	void MergeLeg(OneLeg ol)
	{
		if ((olconn == null) || (njl == olconn.length)) 
		{
			OneLeg newolconn[] = new OneLeg[olconn != null ? olconn.length * 2 : 4];
			for (int i = 0; i < njl; i++)
				newolconn[i] = olconn[i];
			olconn = newolconn;
		}
		olconn[njl] = ol;
		njl++;
	}

	/////////////////////////////////////////////
	// transformed for viewing points
	void SetTLoc(Matrix3D mat)
	{
		tLoc.x = Loc.x * mat.xx + Loc.y * mat.xy + Loc.z * mat.xz + mat.xo;
		tLoc.y = Loc.x * mat.yx + Loc.y * mat.yy + Loc.z * mat.yz + mat.yo;
		tLoc.z = Loc.x * mat.zx + Loc.y * mat.zy + Loc.z * mat.zz + mat.zo;

		TLocX = (int)tLoc.x;
		TLocY = (int)tLoc.y;
		TLocZ = (int)tLoc.z;
	}
			

	/////////////////////////////////////////////
	int sqDist(int mx, int my)
	{
		int dx = TLocX - mx;
		int dy = TLocY - my;
		return dx * dx + dy * dy;
	}


	/////////////////////////////////////////////
	// used in wireframe graphics.  
	void paintW(Graphics g, boolean bActive, boolean bLong)
	{
		g.setColor(bActive ? TN.wfmpointActive : TN.wfmpointInactive); 
		g.drawRect(TLocX - TN.xsgPointSize, TLocY - TN.xsgPointSize, 2 * TN.xsgPointSize, 2 * TN.xsgPointSize);
		g.setColor(bActive ? TN.wfmnameActive : TN.wfmnameInactive);
System.out.println((bLong ? utunnel.fullname + TN.StationDelimeter + name : name)); 
		g.drawString((bLong ? utunnel.fullname + TN.StationDelimeter + name : name), TLocX + TN.xsgPointSize * 2, TLocY + TN.xsgPointSize * 2);
	}
}

